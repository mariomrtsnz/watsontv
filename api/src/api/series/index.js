import { Router } from 'express'
import { middleware as query, Schema } from 'querymen'
import { middleware as body } from 'bodymen'
import { token } from '../../services/passport'
import { create, index, show, update, destroy, allSeriesAndAttributes } from './controller'
import { schema } from './model'
export Series, { schema } from './model'

const router = new Router()
const { title, releaseDate, rating, cast, coverImage, genre, synopsis, broadcaster, seasons, airsDayOfWeek } = schema.tree
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
 * @api {post} /series Create series
 * @apiName CreateSeries
 * @apiGroup Series
 * @apiPermission admin
 * @apiParam {String} access_token admin access token.
 * @apiParam broadcaster Series's broadcaster.
 * @apiParam seasons Series's seasons.
 * @apiSuccess {Object} series Series's data.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 * @apiError 404 Series not found.
 * @apiError 401 admin access only.
 */
router.post('/',
  token({ required: true, roles: ['admin'] }),
  body({ title, coverImage, genre, synopsis, broadcaster, airsDayOfWeek }),
  create)

/**
 * @api {get} /series Retrieve series
 * @apiName RetrieveSeries
 * @apiGroup Series
 * @apiUse listParams
 * @apiSuccess {Number} count Total amount of series.
 * @apiSuccess {Object[]} rows List of series.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 */
router.get('/',
  query(genreSchema),
  index)

  /**
 * @api {get} /series/user Get All Series with associated Logged User's attributes
 * @apiName RetrieveSeriesWithAttributes
 * @apiGroup Series
 * @apiPermission token
 * @apiSuccess {Object[]} Series.
 */
router.get('/user',
  token({ required: true }),
  query(genreSchema),
  allSeriesAndAttributes)

/**
 * @api {get} /series/:id Retrieve series
 * @apiName RetrieveSeries
 * @apiGroup Series
 * @apiSuccess {Object} series Series's data.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 * @apiError 404 Series not found.
 */
router.get('/:id',
  show)

/**
 * @api {put} /series/:id Update series
 * @apiName UpdateSeries
 * @apiGroup Series
 * @apiPermission admin
 * @apiParam {String} access_token admin access token.
 * @apiSuccess {Object} series Series's data.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 * @apiError 404 Series not found.
 * @apiError 401 admin access only.
 */
router.put('/:id',
  token({ required: true, roles: ['admin'] }),
  body({ title, coverImage, genre, synopsis, broadcaster, airsDayOfWeek }),
  update)

/**
 * @api {delete} /series/:id Delete series
 * @apiName DeleteSeries
 * @apiGroup Series
 * @apiPermission admin
 * @apiParam {String} access_token admin access token.
 * @apiSuccess (Success 204) 204 No Content.
 * @apiError 404 Series not found.
 * @apiError 401 admin access only.
 */
router.delete('/:id',
  token({ required: true, roles: ['admin'] }),
  destroy)

export default router
