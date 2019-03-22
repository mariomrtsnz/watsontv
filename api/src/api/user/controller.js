import _ from 'lodash';

import { success, notFound } from '../../services/response/'
import { User } from '.'
import { Genre } from '../genre'
import { Episode } from '../episode'
import { Season } from '../season'
import { sign } from '../../services/jwt'

export const index = ({ querymen: { query, select, cursor }, user }, res, next) =>
  User.count(query)
    .then(count => User.find(query, select, cursor)
      .then(users => ({
        count,
        rows: users.map((foundUser) => {
          if (user.friends.length != 0) {
            if (user.friends.indexOf(foundUser.id) != -1)
              foundUser.set('isFriend', true)
            else
              foundUser.set('isFriend', false)
          } else
            foundUser.set('isFriend', false)
          return foundUser;
        })
      }))
    )
    .then(success(res))
    .catch(next)

export const show = ({ params, user }, res, next) => {
  const foundInFriends = user.friends.indexOf(params.id);
  User.findById(params.id)
    .then((user) => {
      if (foundInFriends != -1) {
        user.set('isFriend', true);
      } else {
        user.set('isFriend', false);
      }
      return user;
    })
    .then(success(res))
    .catch(next)
}

export const showMe = ({ user }, res) =>
  res.json(user.view(true))

export const create = ({ bodymen: { body } }, res, next) => {
  // TODO: Assign a 'Watchlist' and 'Favorites' by default.
  User.create(body)
    .then(user => {
      sign(user.id)
        .then((token) => ({ token, user: user.view(true) }))
        .then(success(res, 201))
    })
    .catch((err) => {
      /* istanbul ignore else */
      if (err.name === 'MongoError' && err.code === 11000) {
        res.status(409).json({
          valid: false,
          param: 'email',
          message: 'email already registered'
        })
      } else {
        next(err)
      }
    })
}

export const update = ({ bodymen: { body }, params, user }, res, next) =>
  User.findById(params.id === 'me' ? user.id : params.id)
    .then(notFound(res))
    .then((result) => {
      if (!result) return null
      const isAdmin = user.role === 'admin'
      const isSelfUpdate = user.id === result.id
      if (!isSelfUpdate && !isAdmin) {
        res.status(401).json({
          valid: false,
          message: 'You can\'t change other user\'s data'
        })
        return null
      }
      return result
    })
    .then((user) => user ? Object.assign(user, body).save() : null)
    .then((user) => user ? user.view(true) : null)
    .then(success(res))
    .catch(next)

export const updatePassword = ({ bodymen: { body }, params, user }, res, next) =>
  User.findById(params.id === 'me' ? user.id : params.id)
    .then(notFound(res))
    .then((result) => {
      if (!result) return null
      const isSelfUpdate = user.id === result.id
      if (!isSelfUpdate) {
        res.status(401).json({
          valid: false,
          param: 'password',
          message: 'You can\'t change other user\'s password'
        })
        return null
      }
      return result
    })
    .then((user) => user ? user.set({ password: body.password }).save() : null)
    .then((user) => user ? user.view(true) : null)
    .then(success(res))
    .catch(next)

export const destroy = ({ params }, res, next) =>
  User.findById(params.id)
    .then(notFound(res))
    .then((user) => user ? user.remove() : null)
    .then(success(res, 204))
    .catch(next)


// CUSTOM CONTROLLERS

export const befriended = ({querymen: query, select, cursor, user }, res, next) => {
  User.find({'_id': {$in: user.friends}}).populate('badges', 'id points').populate('likes', 'id name').populate('language').then(users => {
    return new Promise(function (res, rej) {
      users.map((foundUser) => {
        foundUser.set('isFriend', true);
      });
      res(users);
    });
  }).then(success(res)).catch(next);
}

export const updateWatched = ({params, user}, res, next) => {
  const found = user.watched.indexOf(params.id);
  const foundInWatchlisted = user.watchlist.indexOf(params.id);
  if (found != -1)
    user.watched.splice(found, 1);
  else {
    if (foundInWatchlisted != -1) {
      user.watchlist.splice(foundInWatchlisted, 1);
    }
    user.watched.push(params.id);
  }
  user.save()
  .then(success(res))
  .catch(next)
}

export const updateWatchlisted = ({params, user}, res, next) => {
  const found = user.watchlist.indexOf(params.id);
  if (found != -1)
    user.watchlist.splice(found, 1);
  else
    user.watchlist.push(params.id);
  user.save()
  .then(success(res))
  .catch(next)
}

export const editFriended = ({ params, user }, res, next) => {
  const found = user.friends.indexOf(params.id);
  if (found != -1)
    user.friends.splice(found, 1);
  else
    user.friends.push(params.id);
  user.save()
    .then(success(res))
    .catch(next);
}

export const getWatchlist = ({ user }, res, next) => {
  User.findById(user.id).select('watchlist').populate({path: 'watchlist', populate: { path: 'genre' }}).then(watchlist => {
    watchlist.watchlist.map(foundMedia => {
        if (user.watched.length != 0) {
          if (user.watched.indexOf(foundMedia.id) != -1)
            foundMedia.set('watched', true)
          else
            foundMedia.set('watched', false)
        } else
            foundMedia.set('watched', false)
        foundMedia.set('watchlisted', true)
        return foundMedia;
      })
      return watchlist.watchlist;
    })
  .then(success(res))
  .catch(next)
}

export const getGenreStats = ({ params }, res, next) => {
  Array.prototype.multiIndexOf = function (el) { 
    var idxs = [];
    for (var i = this.length - 1; i >= 0; i--) {
        if (this[i] === el) {
            idxs.unshift(i);
        }
    }
    return idxs;
  };
  User.findById(params.id).select('watched').populate({path: 'watched', select: 'genre -_id -__t'}).then(watchedMedia => {
    const watchedGenres = [];
    watchedMedia.watched.forEach(media => {
      watchedGenres.push(media.genre.toString());
    })
    var stats = {};
    Genre.find().then(genres => {
      genres.forEach(genre => {
        const genreOcurrences = watchedGenres.multiIndexOf(genre.id.toString());
        stats[genre.name] = (genreOcurrences.length/watchedMedia.watched.length)*100;
      })
      res.send(stats);
    })
  })
}

export const getTotalWatchedTime = ({ params }, res, next) => {
  const totalWatchedTime = {
    episodes: {
      totalNumber: 0,
      totalTime: 0
    },
    movies: {
      totalNumber: 0,
      totalTime: 0
    }
  }
  User.findById(params.id).select('watched').populate({path: 'watched', model: 'Media', select: 'seasons runtime', populate: {path: 'seasons', model: 'Season', select: 'episodes', populate: {path: 'episodes', model: 'Episode', select: 'duration'}}})
    .then((foundUserWatched) => {
      foundUserWatched.watched.forEach(media => {
        if (media.__t === 'Series') {
          media.seasons.forEach(season => {
            season.episodes.forEach(episode => {
              totalWatchedTime.episodes.totalNumber += 1;
              totalWatchedTime.episodes.totalTime += episode.duration;
            })
          })
        } else {
          totalWatchedTime.movies.totalNumber += 1;
          totalWatchedTime.movies.totalTime += media.runtime
        }
        return totalWatchedTime;
      })
      res.send(totalWatchedTime);
    })
}

export const getDashboardMedia = ({ params, user }, res, next) => {
  console.log(user.watchedEpisodes);
  Episode.find({'_id': {$in: user.watchedEpisodes}}).limit(10).populate({path: 'season', model: 'Season', select: 'series', populate: { path: 'series', model: 'Series' }}).then(episodes => {
    // Si hay dos o mas capitulos de una misma serie, devolver un capitulo de esa serie que no este en la lista del usuario user.watchedEpisodes.
    let count = 0;
    let previousSeriesId;
    episodes.forEach(episode => {
      if (episode.season.series.id === previousSeriesId) {
        count += 1
      } else {
        previousSeriesId = episode.season.series.id;
      }
    })
    if (count >= 2) {
      
    }
    return episodes;
  })
  .then(success(res)).catch(next);
  }