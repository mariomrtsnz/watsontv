import { success, notFound } from '../../services/response/'
import { Genre } from '.'

export const create = ({ bodymen: { body } }, res, next) =>
  Genre.create(body)
    .then((genre) => genre.view(true))
    .then(success(res, 201))
    .catch(next)

export const index = ({ querymen: { query, select, cursor } }, res, next) =>
  Genre.count(query)
    .then(count => Genre.find(query, select, cursor)
      .then((genres) => ({
        count,
        rows: genres.map((genre) => genre.view())
      }))
    )
    .then(success(res))
    .catch(next)

export const show = ({ params }, res, next) =>
  Genre.findById(params.id)
    .then(notFound(res))
    .then((genre) => genre ? genre.view() : null)
    .then(success(res))
    .catch(next)

export const update = ({ bodymen: { body }, params }, res, next) =>
  Genre.findById(params.id)
    .then(notFound(res))
    .then((genre) => genre ? Object.assign(genre, body).save() : null)
    .then((genre) => genre ? genre.view(true) : null)
    .then(success(res))
    .catch(next)

export const destroy = ({ params }, res, next) =>
  Genre.findById(params.id)
    .then(notFound(res))
    .then((genre) => genre ? genre.remove() : null)
    .then(success(res, 204))
    .catch(next)
