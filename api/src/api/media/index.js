import { Router } from 'express'
import { middleware as query } from 'querymen'
import { middleware as body } from 'bodymen'
import { token } from '../../services/passport'
import { create, index, show, update, destroy, addCastMember } from './controller'
import { schema } from './model'
export Media, { schema } from './model'

const router = new Router()
const { title, releaseDate, rating, cast, coverImage, genre, synopsis } = schema.tree

/**
 * @api {post} /media Create media
 * @apiName CreateMedia
 * @apiGroup Media
 * @apiPermission admin
 * @apiParam {String} access_token admin access token.
 * @apiParam title Media's title.
 * @apiParam releaseDate Media's releaseDate.
 * @apiParam rating Media's rating.
 * @apiParam cast Media's cast.
 * @apiParam coverImage Media's coverImage.
 * @apiParam genre Media's genre.
 * @apiParam synopsis Media's synopsis.
 * @apiSuccess {Object} media Media's data.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 * @apiError 404 Media not found.
 * @apiError 401 admin access only.
 */
router.post('/',
  token({ required: true, roles: ['admin'] }),
  body({ title, releaseDate, rating, cast, coverImage, genre, synopsis }),
  create)

/**
 * @api {get} /media Retrieve media
 * @apiName RetrieveMedia
 * @apiGroup Media
 * @apiUse listParams
 * @apiSuccess {Number} count Total amount of media.
 * @apiSuccess {Object[]} rows List of media.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 */
router.get('/',
  query(),
  index)

/**
 * @api {get} /media/:id Retrieve media
 * @apiName RetrieveMedia
 * @apiGroup Media
 * @apiSuccess {Object} media Media's data.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 * @apiError 404 Media not found.
 */
router.get('/:id',
  show)

/**
 * @api {put} /media/:id Update media
 * @apiName UpdateMedia
 * @apiGroup Media
 * @apiPermission admin
 * @apiParam {String} access_token admin access token.
 * @apiParam title Media's title.
 * @apiParam releaseDate Media's releaseDate.
 * @apiParam rating Media's rating.
 * @apiParam cast Media's cast.
 * @apiParam coverImage Media's coverImage.
 * @apiParam genre Media's genre.
 * @apiParam synopsis Media's synopsis.
 * @apiSuccess {Object} media Media's data.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 * @apiError 404 Media not found.
 * @apiError 401 admin access only.
 */
router.put('/:id',
  token({ required: true, roles: ['admin'] }),
  body({ title, releaseDate, rating, cast, coverImage, genre, synopsis }),
  update)

router.put('/:id/addCast',
  token({ required: true, roles: ['admin'] }),
  body({ cast }),
  addCastMember)

/**
 * @api {delete} /media/:id Delete media
 * @apiName DeleteMedia
 * @apiGroup Media
 * @apiPermission admin
 * @apiParam {String} access_token admin access token.
 * @apiSuccess (Success 204) 204 No Content.
 * @apiError 404 Media not found.
 * @apiError 401 admin access only.
 */
router.delete('/:id',
  token({ required: true, roles: ['admin'] }),
  destroy)

export default router
