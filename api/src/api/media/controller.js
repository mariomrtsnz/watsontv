import _ from 'lodash';

import { success, notFound } from '../../services/response/'
import { Media } from '.'
import { Collection } from 'mongoose';

export const create = ({ bodymen: { body } }, res, next) =>
  Media.create(body)
    .then((media) => media.view(true))
    .then(success(res, 201))
    .catch(next)

export const index = ({ querymen: { query, select, cursor } }, res, next) => {
  Media.count(query)
    .then(count => Media.find(query, select, cursor).populate('genre')
      .then((media) => ({
        count,
        rows: media.map((media) => media.view())
      }))
    )
    .then(success(res))
    .catch(next)
}

export const show = ({ params, user }, res, next) =>
  Media.findById(params.id).populate('genre').populate('seasons', 'id number episodes').populate('cast', 'id name picture')
    .then(notFound(res))
    .then((media) =>  {
      if (user.watched.length != 0) {
        if (user.watched.indexOf(media.id) != -1)
          media.set('watched', true)
        else
          media.set('watched', false)
      } else
          media.set('watched', false)
      if (user.watchlist.length != 0) {
        if (user.watchlist.indexOf(media.id) != -1)
          media.set('watchlisted', true)
        else
          media.set('watchlisted', false)
      } else
        media.set('watchlisted', false)
      return media;
    })
    .then(success(res))
    .catch(next)

export const update = ({ bodymen: { body }, params }, res, next) =>
  Media.findById(params.id)
    .then(notFound(res))
    .then((media) => media ? Object.assign(media, body).save() : null)
    .then((media) => media ? media.view(true) : null)
    .then(success(res))
    .catch(next)

export const destroy = ({ params }, res, next) =>
  Media.findById(params.id)
    .then(notFound(res))
    .then((media) => media ? media.remove() : null)
    .then(success(res, 204))
    .catch(next)

export const addCastMember = ({ bodymen: { body }, params }, res, next) =>
  Media.findByIdAndUpdate(params.id, { $addToSet: {cast: body.cast } }, {new: true})
    .then(notFound(res))
    .then(success(res))
    .catch(next)

export const removeCastMember = ({ bodymen: { body }, params }, res, next) =>
  Media.findByIdAndUpdate(params.id, { $pull: {cast: body.cast } }, {new: true})
    .then(notFound(res))
    .then(success(res))
    .catch(next)

export const allMediaAndAttributes = ({ querymen: { query, select, cursor }, user }, res, next) => {
  // Collection.find(ownerId: user.id).then(collections => {

  // })
  Media.count(query)
  .then(count => Media.find(query, select, cursor).populate('genre')
    .then((media) => ({
      count,
      rows: media.map(foundMedia => {
        if (user.watched.length != 0) {
          if (user.watched.indexOf(foundMedia.id) != -1)
            foundMedia.set('watched', true)
          else
            foundMedia.set('watched', false)
        } else
            foundMedia.set('watched', false)
        if (user.watchlist.length != 0) {
          if (user.watchlist.indexOf(foundMedia.id) != -1)
            foundMedia.set('watchlisted', true)
          else
            foundMedia.set('watchlisted', false)
        } else
            foundMedia.set('watchlisted', false)
        return foundMedia;
      })
    }))
  )
  .then(success(res))
  .catch(next)
}