import { success, notFound } from '../../services/response/'
import { Season } from '.'
import { Series } from '../series'
import { Episode } from '../episode'

export const create = ({ bodymen: { body } }, res, next) => {
  Season.create(body)
  .then((season) => {
    season.view(true);
    Series.findByIdAndUpdate(
      { _id: season.series },
      { $push: {seasons: season } },
      {new: true})
      .then(success(res, 200)).catch(next);
  })
    .then(success(res, 201))
    .catch(next)
}

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
  Season.findById(params.id).populate('episodes').populate('series', 'title')
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
    .then((season) => {
      Series.findByIdAndUpdate(
        { _id: season.series },
        { $pull: {seasons: params.id } },
        { new: true })
        .then(success(res, 200)).catch(next);
      Episode.deleteMany({season: params.id}).then(success(res, 204)).catch(next);
      season ? season.remove() : null
      return null;
    })
    .then(success(res, 204))
    .catch(next)

export const showEpisodes = ({ params }, res, next) =>
  Episode.find({"season": params.id})
    .then(notFound(res))
    .then(success(res))
    .catch(next)