import { Router } from 'express'
import { middleware as query } from 'querymen'
import { middleware as body } from 'bodymen'
import { create, index, show, update, destroy, userCollections } from './controller'
import { schema } from './model'
import { token } from '../../services/passport'
export Collection, { schema } from './model'

const router = new Router()
const { name, description, collected, owner } = schema.tree

/**
 * @api {post} /collections Create collection
 * @apiName CreateCollection
 * @apiGroup Collection
 * @apiParam name Collection's name.
 * @apiParam description Collection's description.
 * @apiParam collected Collection's collected.
 * @apiSuccess {Object} collection Collection's data.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 * @apiError 404 Collection not found.
 */
router.post('/',
  body({ name, description, collected, owner }),
  create)

/**
 * @api {get} /collections Retrieve collections
 * @apiName RetrieveCollections
 * @apiGroup Collection
 * @apiUse listParams
 * @apiSuccess {Number} count Total amount of collections.
 * @apiSuccess {Object[]} rows List of collections.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 */
router.get('/',
  query(),
  index)

router.get('/user/:id',
  token({ required: true }),
  userCollections)

/**
 * @api {get} /collections/:id Retrieve collection
 * @apiName RetrieveCollection
 * @apiGroup Collection
 * @apiSuccess {Object} collection Collection's data.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 * @apiError 404 Collection not found.
 */
router.get('/:id',
  show)

/**
 * @api {put} /collections/:id Update collection
 * @apiName UpdateCollection
 * @apiGroup Collection
 * @apiParam name Collection's name.
 * @apiParam description Collection's description.
 * @apiParam collected Collection's collected.
 * @apiSuccess {Object} collection Collection's data.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 * @apiError 404 Collection not found.
 */
router.put('/:id',
  body({ name, description, collected }),
  update)

/**
 * @api {delete} /collections/:id Delete collection
 * @apiName DeleteCollection
 * @apiGroup Collection
 * @apiSuccess (Success 204) 204 No Content.
 * @apiError 404 Collection not found.
 */
router.delete('/:id',
  destroy)

export default router
