import { success, notFound } from '../../services/response/'
import { Movie } from '.'
const fetch = require('node-fetch');

export const create = ({ body }, res, next) =>
fetch(`http://www.omdbapi.com/?s=${body.title}&apikey=d29a85f5`)
  .then(
    response => {
        return response.json();
    }
  ).then(json => {
    let result = JSON.parse(JSON.stringify(json));
    body.coverImage = result.Search[0].Poster;
    Movie.create(body)
      .then((movie) => movie.view(true))
      .then(success(res, 201))
      .catch(next) 
  }).catch(next)

export const index = ({ querymen: { query, select, cursor } }, res, next) =>
  Movie.count(query)
    .then(count => Movie.find(query, select, cursor).populate('genre')
      .then((movies) => ({
        count,
        rows: movies.map((movie) => movie.view())
      }))
    )
    .then(success(res))
    .catch(next)

export const show = ({ params }, res, next) =>
  Movie.findById(params.id)
    .then(notFound(res))
    .then((movie) => movie ? movie.view() : null)
    .then(success(res))
    .catch(next)

export const update = ({ body, params }, res, next) =>
  Movie.findById(params.id)
    .then(notFound(res))
    .then((movie) => movie ? Object.assign(movie, body).save() : null)
    .then((movie) => movie ? movie.view(true) : null)
    .then(success(res))
    .catch(next)

export const destroy = ({ params }, res, next) =>
  Movie.findById(params.id)
    .then(notFound(res))
    .then((movie) => movie ? movie.remove() : null)
    .then(success(res, 204))
    .catch(next)
