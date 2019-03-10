import { Router } from 'express'
import { middleware as query } from 'querymen'
import { middleware as body } from 'bodymen'
import { token } from '../../services/passport'
import { create, index, show, update, destroy } from './controller'
import { schema } from './model'
export Genre, { schema } from './model'

const router = new Router()
const { name } = schema.tree

/**
 * @api {post} /genres Create genre
 * @apiName CreateGenre
 * @apiGroup Genre
 * @apiPermission admin
 * @apiParam {String} access_token admin access token.
 * @apiParam name Genre's name.
 * @apiSuccess {Object} genre Genre's data.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 * @apiError 404 Genre not found.
 * @apiError 401 admin access only.
 */
router.post('/',
  token({ required: true, roles: ['admin'] }),
  body({ name }),
  create)

/**
 * @api {get} /genres Retrieve genres
 * @apiName RetrieveGenres
 * @apiGroup Genre
 * @apiUse listParams
 * @apiSuccess {Number} count Total amount of genres.
 * @apiSuccess {Object[]} rows List of genres.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 */
router.get('/',
  query(),
  index)

/**
 * @api {get} /genres/:id Retrieve genre
 * @apiName RetrieveGenre
 * @apiGroup Genre
 * @apiSuccess {Object} genre Genre's data.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 * @apiError 404 Genre not found.
 */
router.get('/:id',
  show)

/**
 * @api {put} /genres/:id Update genre
 * @apiName UpdateGenre
 * @apiGroup Genre
 * @apiPermission admin
 * @apiParam {String} access_token admin access token.
 * @apiParam name Genre's name.
 * @apiSuccess {Object} genre Genre's data.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 * @apiError 404 Genre not found.
 * @apiError 401 admin access only.
 */
router.put('/:id',
  token({ required: true, roles: ['admin'] }),
  body({ name }),
  update)

/**
 * @api {delete} /genres/:id Delete genre
 * @apiName DeleteGenre
 * @apiGroup Genre
 * @apiPermission admin
 * @apiParam {String} access_token admin access token.
 * @apiSuccess (Success 204) 204 No Content.
 * @apiError 404 Genre not found.
 * @apiError 401 admin access only.
 */
router.delete('/:id',
  token({ required: true, roles: ['admin'] }),
  destroy)

export default router
