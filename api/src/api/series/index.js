import { Router } from 'express'
import { middleware as query } from 'querymen'
import { middleware as body } from 'bodymen'
import { token } from '../../services/passport'
import { create, index, show, update, destroy } from './controller'
import { schema } from './model'
export Series, { schema } from './model'

const router = new Router()
const { title, releaseDate, rating, cast, coverImage, genre, synopsis, broadcaster, seasons } = schema.tree

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
  body({ title, coverImage, genre, synopsis, broadcaster }),
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
  query(),
  index)

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
 * @apiParam broadcaster Series's broadcaster.
 * @apiParam seasons Series's seasons.
 * @apiSuccess {Object} series Series's data.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 * @apiError 404 Series not found.
 * @apiError 401 admin access only.
 */
router.put('/:id',
  token({ required: true, roles: ['admin'] }),
  body({ broadcaster, seasons }),
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
