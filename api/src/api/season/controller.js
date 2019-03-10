import { success, notFound } from '../../services/response/'
import { Season } from '.'

export const create = ({ bodymen: { body } }, res, next) =>
  Season.create(body)
    .then((season) => season.view(true))
    .then(success(res, 201))
    .catch(next)

export const index = ({ querymen: { query, select, cursor } }, res, next) =>
  Season.count(query)
    .then(count => Season.find(query, select, cursor)
      .then((seasons) => ({
        count,
        rows: seasons.map((season) => season.view())
      }))
    )
    .then(success(res))
    .catch(next)

export const show = ({ params }, res, next) =>
  Season.findById(params.id)
    .then(notFound(res))
    .then((season) => season ? season.view() : null)
    .then(success(res))
    .catch(next)

export const update = ({ bodymen: { body }, params }, res, next) =>
  Season.findById(params.id)
    .then(notFound(res))
    .then((season) => season ? Object.assign(season, body).save() : null)
    .then((season) => season ? season.view(true) : null)
    .then(success(res))
    .catch(next)

export const destroy = ({ params }, res, next) =>
  Season.findById(params.id)
    .then(notFound(res))
    .then((season) => season ? season.remove() : null)
    .then(success(res, 204))
    .catch(next)
