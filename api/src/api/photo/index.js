import { Router } from 'express'
import { middleware as query } from 'querymen'
import { middleware as body } from 'bodymen'
import { token } from '../../services/passport'
import { create, index, show, update, destroy } from './controller'
import { schema } from './model'
export Photo, { schema } from './model'

const router = new Router()
const { imgurlink, deletehash } = schema.tree


const multer = require('multer')
const storage = multer.memoryStorage()
const upload = multer({storage: storage})

/**
 * @api {post} /photos Create photo
 * @apiName CreatePhoto
 * @apiGroup Photo
 * @apiPermission admin
 * @apiParam {String} access_token admin access token.
 * @apiParam imgurlink Photo's imgurlink.
 * @apiParam deletehash Photo's deletehash.
 * @apiSuccess {Object} photo Photo's data.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 * @apiError 404 Photo not found.
 * @apiError 401 admin access only.
 */
router.post('/',
  token({ required: true, roles: ['admin'] }),
  upload.single('photo'),
  create)

/**
 * @api {get} /photos Retrieve photos
 * @apiName RetrievePhotos
 * @apiGroup Photo
 * @apiUse listParams
 * @apiSuccess {Number} count Total amount of photos.
 * @apiSuccess {Object[]} rows List of photos.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 */
router.get('/',
  query(),
  index)

/**
 * @api {get} /photos/:id Retrieve photo
 * @apiName RetrievePhoto
 * @apiGroup Photo
 * @apiSuccess {Object} photo Photo's data.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 * @apiError 404 Photo not found.
 */
router.get('/:id',
  show)

/**
 * @api {put} /photos/:id Update photo
 * @apiName UpdatePhoto
 * @apiGroup Photo
 * @apiPermission admin
 * @apiParam {String} access_token admin access token.
 * @apiParam imgurlink Photo's imgurlink.
 * @apiParam deletehash Photo's deletehash.
 * @apiSuccess {Object} photo Photo's data.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 * @apiError 404 Photo not found.
 * @apiError 401 admin access only.
 */
router.put('/:id',
  token({ required: true, roles: ['admin'] }),
  body({ imgurlink, deletehash }),
  update)

/**
 * @api {delete} /photos/:id Delete photo
 * @apiName DeletePhoto
 * @apiGroup Photo
 * @apiPermission admin
 * @apiParam {String} access_token admin access token.
 * @apiSuccess (Success 204) 204 No Content.
 * @apiError 404 Photo not found.
 * @apiError 401 admin access only.
 */
router.delete('/:id',
  token({ required: true, roles: ['admin'] }),
  destroy)

export default router
