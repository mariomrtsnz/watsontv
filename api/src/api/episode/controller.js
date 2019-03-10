import { success, notFound } from '../../services/response/'
import { Episode } from '.'

export const create = ({ bodymen: { body } }, res, next) =>
  Episode.create(body)
    .then((episode) => episode.view(true))
    .then(success(res, 201))
    .catch(next)

export const index = ({ querymen: { query, select, cursor } }, res, next) =>
  Episode.count(query)
    .then(count => Episode.find(query, select, cursor)
      .then((episodes) => ({
        count,
        rows: episodes.map((episode) => episode.view())
      }))
    )
    .then(success(res))
    .catch(next)

export const show = ({ params }, res, next) =>
  Episode.findById(params.id)
    .then(notFound(res))
    .then((episode) => episode ? episode.view() : null)
    .then(success(res))
    .catch(next)

export const update = ({ bodymen: { body }, params }, res, next) =>
  Episode.findById(params.id)
    .then(notFound(res))
    .then((episode) => episode ? Object.assign(episode, body).save() : null)
    .then((episode) => episode ? episode.view(true) : null)
    .then(success(res))
    .catch(next)

export const destroy = ({ params }, res, next) =>
  Episode.findById(params.id)
    .then(notFound(res))
    .then((episode) => episode ? episode.remove() : null)
    .then(success(res, 204))
    .catch(next)
