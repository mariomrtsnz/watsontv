import { success, notFound } from '../../services/response/'
import { Collection } from '.'

export const create = ({ bodymen: { body } }, res, next) =>
  Collection.create(body)
    .then((collection) => collection.view(true))
    .then(success(res, 201))
    .catch(next)

export const index = ({ querymen: { query, select, cursor } }, res, next) =>
  Collection.count(query)
    .then(count => Collection.find(query, select, cursor)
      .then((collections) => ({
        count,
        rows: collections.map((collection) => collection.view())
      }))
    )
    .then(success(res))
    .catch(next)

export const userCollections = ({ params, user }, res, next) => {
  Collection.find({"owner": user.id}).then(success(res)).catch(next);
}

export const show = ({ params }, res, next) =>
  Collection.findById(params.id)
    .then(notFound(res))
    .then((collection) => collection ? collection.view() : null)
    .then(success(res))
    .catch(next)

export const update = ({ bodymen: { body }, params }, res, next) =>
  Collection.findById(params.id)
    .then(notFound(res))
    .then((collection) => collection ? Object.assign(collection, body).save() : null)
    .then((collection) => collection ? collection.view(true) : null)
    .then(success(res))
    .catch(next)

export const destroy = ({ params }, res, next) =>
  Collection.findById(params.id)
    .then(notFound(res))
    .then((collection) => collection ? collection.remove() : null)
    .then(success(res, 204))
    .catch(next)
