import { success, notFound } from '../../services/response/'
import { Media } from '.'

export const create = ({ bodymen: { body } }, res, next) =>
  Media.create(body)
    .then((media) => media.view(true))
    .then(success(res, 201))
    .catch(next)

export const index = ({ querymen: { query, select, cursor } }, res, next) =>
  Media.count(query)
    .then(count => Media.find(query, select, cursor).populate('genre')
      .then((media) => ({
        count,
        rows: media.map((media) => media.view())
      }))
    )
    .then(success(res))
    .catch(next)

export const show = ({ params }, res, next) =>
  Media.findById(params.id).populate('genre').populate('seasons', 'id number episodes').populate('cast', 'id name picture')
    .then(notFound(res))
    .then((media) => media ? media.view() : null)
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
  Media.findByIdAndUpdate(params.id, { $push: {cast: body.cast } }, {new: true})
    .then(notFound(res))
    .then(success(res))
    .catch(next)

export const removeCastMember = ({ bodymen: { body }, params }, res, next) =>
  Media.findByIdAndUpdate(params.id, { $pull: {cast: body.cast } }, {new: true})
    .then(notFound(res))
    .then(success(res))
    .catch(next)