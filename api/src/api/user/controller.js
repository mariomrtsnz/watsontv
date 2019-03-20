import _ from 'lodash';

import { success, notFound } from '../../services/response/'
import { User } from '.'
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

export const show = ({ params }, res, next) =>
  User.findById(params.id)
    .then(notFound(res))
    .then((user) => user ? user.view() : null)
    .then(success(res))
    .catch(next)

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

export const getTotalWatchedTime = ({ params }, res, next) => {
  User.findById(params.id)
    .then(notFound(res))
    .then((user) => user ? user.view() : null)
    .then(success(res))
    .catch(next)
}

export const befriended = ({ user }, res, next) => {
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