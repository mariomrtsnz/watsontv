import _ from 'lodash';

import { success, notFound } from '../../services/response/'
import { Series } from '.'
import { Season } from '../season'
import { Episode } from '../episode';
const fetch = require('node-fetch');

export const create = ({ bodymen: { body } }, res, next) => {
  fetch(`http://www.omdbapi.com/?s=${body.title}&apikey=d29a85f5`)
  .then(
    response => {
        return response.json();
    }
  ).then(json => {
    let result = JSON.parse(JSON.stringify(json));
    body.coverImage = result.Search[0].Poster;
    Series.create(body)
      .then((series) => series.view(true))
      .then(success(res, 201))
      .catch(next)
  }).catch(next);
}

export const index = ({ querymen: { query, select, cursor } }, res, next) =>
  Series.count(query)
    .then(count => Series.find(query, select, cursor).populate('genre')
      .then((series) => ({
        count,
        rows: series.map((series) => series.view())
      }))
    )
    .then(success(res))
    .catch(next)

export const show = ({ params }, res, next) =>
  Series.findById(params.id)
    .then(notFound(res))
    .then((series) => series ? series.view() : null)
    .then(success(res))
    .catch(next)

export const update = ({ bodymen: { body }, params }, res, next) => {
  fetch(`http://www.omdbapi.com/?s=${body.title}&apikey=d29a85f5`)
  .then(
    response => {
        return response.json();
    }
  ).then(json => {
    let result = JSON.parse(JSON.stringify(json));
    body.coverImage = result.Search[0].Poster;
    Series.findById(params.id)
      .then(notFound(res))
      .then((series) => series ? Object.assign(series, body).save() : null)
      .then((series) => series ? series.view(true) : null)
      .then(success(res))
      .catch(next)})
      .catch(next)
}

export const destroy = ({ params }, res, next) =>
  Series.findById(params.id)
    .then(notFound(res))
    .then((series) => {
      series ? series.remove() : null
      Season.deleteMany({series: params.id}).then(success(res, 204)).catch(next)
    })
    .then(success(res, 204))
    .catch(next)

export const allSeriesAndAttributes = ({ querymen: { query, select, cursor }, user }, res, next) => {
  console.log(user);
  // Collection.find(ownerId: user.id).then(collections => {

  // })
  Series.count(query)
  .then(count => Series.find(query, select, cursor).populate('genre')
    .then((media) => ({
      count,
      rows: media.map(foundMedia => {
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
    }))
  )
  .then(success(res))
  .catch(next)
}