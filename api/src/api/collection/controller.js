import { success, notFound } from '../../services/response/'
import { Collection } from '.'
import { Media } from '../media'

export const create = ({ bodymen: { body } }, res, next) =>
  Collection.create(body)
    .then((collection) => collection.view(true))
    .then(success(res, 201))
    .catch(next)

export const index = ({ querymen: { query, select, cursor } }, res, next) =>
  Collection.count(query)
    .then(count => Collection.find(query, select, cursor).populate('owner', 'id name')
      .then((collections) => ({
        count,
        rows: collections.map((collection) => collection.view())
      }))
    )
    .then(success(res))
    .catch(next)

export const userCollections = ({ params }, res, next) => {
  Collection.find({"owner": params.id}).then(success(res)).catch(next);
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

export const updateCollected = function (req, res) {
  req.body.forEach(collectionId => {
    Collection.findById(collectionId).then(collection => {
      const found = collection.collected.indexOf(req.params.mediaId);
      if (found != -1)
        collection.collected.splice(found, 1);
      else
        collection.collected.push(req.params.mediaId);
      collection.save();
      return collection;
    })
  });
  res.status(200).send(req.body.collectionsIds);
}

export const getCollectionMedia = ({ querymen: { query, select, cursor }, params, user }, res, next) => {
  Collection.findById(params.id).select('collected').populate({path: 'collected', populate: { path: 'genre' }}).then(collection => {
    collection.collected.map(foundMedia => {
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
      return collection.collected;
    })
  .then(success(res))
  .catch(next)
  }