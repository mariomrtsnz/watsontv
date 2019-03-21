import { Router } from 'express'
import { middleware as query, Schema } from 'querymen'
import { middleware as body } from 'bodymen'
import { token } from '../../services/passport'
import { create, index, show, update, destroy, allMoviesAndAttributes } from './controller'
import { schema } from './model'
export Movie, { schema } from './model'

const router = new Router()
const { title, releaseDate, rating, cast, coverImage, genre, synopsis, runtime } = schema.tree
const genreSchema = new Schema({
  genre: {
    type: String,
    paths: ['genre']
  },
  title: {
    type: RegExp,
    paths: ['title']
  }
})

/**
 * @api {post} /movies Create movie
 * @apiName CreateMovie
 * @apiGroup Movie
 * @apiPermission admin
 * @apiParam {String} access_token admin access token.
 * @apiSuccess {Object} movie Movie's data.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 * @apiError 404 Movie not found.
 * @apiError 401 admin access only.
 */
router.post('/',
  token({ required: true, roles: ['admin'] }),
  body({ title, releaseDate, coverImage, genre, synopsis, runtime }),
  create)

/**
 * @api {get} /movies Retrieve movies
 * @apiName RetrieveMovies
 * @apiGroup Movie
 * @apiUse listParams
 * @apiSuccess {Number} count Total amount of movies.
 * @apiSuccess {Object[]} rows List of movies.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 */
router.get('/',
  query(genreSchema),
  index)

router.get('/user',
  token({ required: true }),
  query(genreSchema),
  allMoviesAndAttributes)

/**
 * @api {get} /movies/:id Retrieve movie
 * @apiName RetrieveMovie
 * @apiGroup Movie
 * @apiSuccess {Object} movie Movie's data.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 * @apiError 404 Movie not found.
 */
router.get('/:id',
  show)

/**
 * @api {put} /movies/:id Update movie
 * @apiName UpdateMovie
 * @apiGroup Movie
 * @apiPermission admin
 * @apiParam {String} access_token admin access token.
 * @apiSuccess {Object} movie Movie's data.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 * @apiError 404 Movie not found.
 * @apiError 401 admin access only.
 */
router.put('/:id',
  token({ required: true, roles: ['admin'] }),
  update)

/**
 * @api {delete} /movies/:id Delete movie
 * @apiName DeleteMovie
 * @apiGroup Movie
 * @apiPermission admin
 * @apiParam {String} access_token admin access token.
 * @apiSuccess (Success 204) 204 No Content.
 * @apiError 404 Movie not found.
 * @apiError 401 admin access only.
 */
router.delete('/:id',
  token({ required: true, roles: ['admin'] }),
  destroy)

export default router
