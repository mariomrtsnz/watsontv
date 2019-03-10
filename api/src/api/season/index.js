import { Router } from 'express'
import { middleware as query } from 'querymen'
import { middleware as body } from 'bodymen'
import { token } from '../../services/passport'
import { create, index, show, update, destroy } from './controller'
import { schema } from './model'
export Season, { schema } from './model'

const router = new Router()
const { number, episodes } = schema.tree

/**
 * @api {post} /seasons Create season
 * @apiName CreateSeason
 * @apiGroup Season
 * @apiPermission admin
 * @apiParam {String} access_token admin access token.
 * @apiParam number Season's number.
 * @apiParam episodes Season's episodes.
 * @apiSuccess {Object} season Season's data.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 * @apiError 404 Season not found.
 * @apiError 401 admin access only.
 */
router.post('/',
  token({ required: true, roles: ['admin'] }),
  body({ number, episodes }),
  create)

/**
 * @api {get} /seasons Retrieve seasons
 * @apiName RetrieveSeasons
 * @apiGroup Season
 * @apiUse listParams
 * @apiSuccess {Number} count Total amount of seasons.
 * @apiSuccess {Object[]} rows List of seasons.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 */
router.get('/',
  query(),
  index)

/**
 * @api {get} /seasons/:id Retrieve season
 * @apiName RetrieveSeason
 * @apiGroup Season
 * @apiSuccess {Object} season Season's data.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 * @apiError 404 Season not found.
 */
router.get('/:id',
  show)

/**
 * @api {put} /seasons/:id Update season
 * @apiName UpdateSeason
 * @apiGroup Season
 * @apiPermission admin
 * @apiParam {String} access_token admin access token.
 * @apiParam number Season's number.
 * @apiParam episodes Season's episodes.
 * @apiSuccess {Object} season Season's data.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 * @apiError 404 Season not found.
 * @apiError 401 admin access only.
 */
router.put('/:id',
  token({ required: true, roles: ['admin'] }),
  body({ number, episodes }),
  update)

/**
 * @api {delete} /seasons/:id Delete season
 * @apiName DeleteSeason
 * @apiGroup Season
 * @apiPermission admin
 * @apiParam {String} access_token admin access token.
 * @apiSuccess (Success 204) 204 No Content.
 * @apiError 404 Season not found.
 * @apiError 401 admin access only.
 */
router.delete('/:id',
  token({ required: true, roles: ['admin'] }),
  destroy)

export default router
