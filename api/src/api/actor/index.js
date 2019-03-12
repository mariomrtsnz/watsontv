import { Router } from 'express'
import { middleware as query } from 'querymen'
import { middleware as body } from 'bodymen'
import { token } from '../../services/passport'
import { create, index, show, update, destroy, uploadImage } from './controller'
import { schema } from './model'
export Actor, { schema } from './model'

const router = new Router()
const { name, picture } = schema.tree
const multer = require('multer')
const storage = multer.memoryStorage()
const upload = multer({storage: storage})

/**
 * @api {post} /actors Create actor
 * @apiName CreateActor
 * @apiGroup Actor
 * @apiPermission admin
 * @apiParam {String} access_token admin access token.
 * @apiParam name Actor's name.
 * @apiParam picture Actor's picture.
 * @apiSuccess {Object} actor Actor's data.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 * @apiError 404 Actor not found.
 * @apiError 401 admin access only.
 */
router.post('/',
  token({ required: true, roles: ['admin'] }),
  body({ name, picture }),
  create)

/**
 * @api {get} /actors Retrieve actors
 * @apiName RetrieveActors
 * @apiGroup Actor
 * @apiUse listParams
 * @apiSuccess {Number} count Total amount of actors.
 * @apiSuccess {Object[]} rows List of actors.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 */
router.get('/',
  query(),
  index)

/**
 * @api {get} /actors/:id Retrieve actor
 * @apiName RetrieveActor
 * @apiGroup Actor
 * @apiSuccess {Object} actor Actor's data.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 * @apiError 404 Actor not found.
 */
router.get('/:id',
  show)

router.post('/picture',
  token({ required: true, roles: ['admin'] }),
  upload.single('avatar'),
  uploadImage
)

/**
 * @api {put} /actors/:id Update actor
 * @apiName UpdateActor
 * @apiGroup Actor
 * @apiPermission admin
 * @apiParam {String} access_token admin access token.
 * @apiParam name Actor's name.
 * @apiParam picture Actor's picture.
 * @apiSuccess {Object} actor Actor's data.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 * @apiError 404 Actor not found.
 * @apiError 401 admin access only.
 */
router.put('/:id',
  token({ required: true, roles: ['admin'] }),
  body({ name, picture }),
  update)

/**
 * @api {delete} /actors/:id Delete actor
 * @apiName DeleteActor
 * @apiGroup Actor
 * @apiPermission admin
 * @apiParam {String} access_token admin access token.
 * @apiSuccess (Success 204) 204 No Content.
 * @apiError 404 Actor not found.
 * @apiError 401 admin access only.
 */
router.delete('/:id',
  token({ required: true, roles: ['admin'] }),
  destroy)

export default router
