import { success, notFound } from '../../services/response/'
import { Episode } from '.'
import { Season } from '../season'

export const create = ({ bodymen: { body } }, res, next) =>
  Episode.create(body)
  .then((episode) => {
    episode.view(true);
    Season.findByIdAndUpdate(
      { _id: episode.season },
      { $push: {episodes: episode } },
      {new: true})
      .then(success(res, 200)).catch(next);
  })
    .then(success(res, 201))
    .catch(next)

export const index = ({ querymen: { query, select, cursor } }, res, next) =>
  Episode.count(query)
    .then(count => Episode.find(query, select, cursor).populate('season', 'id number')
      .then((episodes) => ({
        count,
        rows: episodes.map((episode) => episode.view())
      }))
    )
    .then(success(res))
    .catch(next)

export const show = ({ params }, res, next) =>
  Episode.findById(params.id).populate('season', 'id number')
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
    .then((episode) => {
      Season.findByIdAndUpdate(
        { _id: episode.season },
        { $pull: {episodes: params.id } },
        { new: true })
        .then(success(res, 200)).catch(next);
      episode ? episode.remove() : null
    })
    .then(success(res, 204))
    .catch(next)
