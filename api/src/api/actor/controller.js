import { success, notFound } from '../../services/response/'
import { Actor } from '.'
const uploadService = require('../../services/upload/')

export const create = (req, res, next) => {  
  uploadService.uploadFromBinary(req.file.buffer)
    .then((json) =>     
      Actor.create({
        name: req.body.name,
        picture: json.data.link
      }))
      .then(actor => actor.view(true))
      .then(success(res, 201))
      .catch(next)
  }



export const index = ({ querymen: { query, select, cursor } }, res, next) =>
  Actor.count(query)
    .then(count => Actor.find(query, select, cursor)
      .then((actors) => ({
        count,
        rows: actors.map((actor) => actor.view())
      }))
    )
    .then(success(res))
    .catch(next)

export const show = ({ params }, res, next) =>
  Actor.findById(params.id)
    .then(notFound(res))
    .then((actor) => actor ? actor.view() : null)
    .then(success(res))
    .catch(next)

export const update = ({ bodymen: { body }, params }, res, next) =>
  Actor.findById(params.id)
    .then(notFound(res))
    .then((actor) => actor ? Object.assign(actor, body).save() : null)
    .then((actor) => actor ? actor.view(true) : null)
    .then(success(res))
    .catch(next)

export const destroy = ({ params }, res, next) =>
  Actor.findById(params.id)
    .then(notFound(res))
    .then((actor) => actor ? actor.remove() : null)
    .then(success(res, 204))
    .catch(next)