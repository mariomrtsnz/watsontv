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
  Media.findById(params.id)
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
