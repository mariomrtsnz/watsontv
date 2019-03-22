import { Router } from 'express'
import { middleware as query } from 'querymen'
import { middleware as body } from 'bodymen'
import { token } from '../../services/passport'
import { create, index, show, update, destroy, updateWatchedEpisodes, updateWatchlistedEpisodes } from './controller'
import { schema } from './model'
export Episode, { schema } from './model'

const router = new Router()
const { name, synopsis, airTime, duration, number, season } = schema.tree

/**
 * @api {post} /episodes Create episode
 * @apiName CreateEpisode
 * @apiGroup Episode
 * @apiPermission admin
 * @apiParam {String} access_token admin access token.
 * @apiParam name Episode's name.
 * @apiParam synopsis Episode's synopsis.
 * @apiParam airTime Episode's airTime.
 * @apiParam duration Episode's duration.
 * @apiSuccess {Object} episode Episode's data.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 * @apiError 404 Episode not found.
 * @apiError 401 admin access only.
 */
router.post('/',
  token({ required: true, roles: ['admin'] }),
  body({ name, synopsis, airTime, duration, number, season }),
  create)

/**
 * @api {get} /episodes Retrieve episodes
 * @apiName RetrieveEpisodes
 * @apiGroup Episode
 * @apiUse listParams
 * @apiSuccess {Number} count Total amount of episodes.
 * @apiSuccess {Object[]} rows List of episodes.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 */
router.get('/',
  query(),
  index)

/**
 * @api {get} /episodes/:id Retrieve episode
 * @apiName RetrieveEpisode
 * @apiGroup Episode
 * @apiSuccess {Object} episode Episode's data.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 * @apiError 404 Episode not found.
 */
router.get('/:id',
  show)

/**
 * @api {get} /episodes/updateWatched/:id Update Logged User's WatchedEpisodes list by Episode Id
 * @apiName UpdateWatchedEpisodes
 * @apiGroup Episode
 * @apiPermission token
 * @apiParam {String} id Episode id
 * @apiUse listParams
 * @apiSuccess {Object} User.
 */
router.put('/updateWatched/:id',
  token({ required: true }),
  updateWatchedEpisodes)

  /**
 * @api {get} /episodes/updateWatchlisted/:id Update Logged User's WatchlistedEpisodes list by Episode Id
 * @apiName UpdateWatchlistedEpisodes
 * @apiGroup Episode
 * @apiPermission token
 * @apiParam {String} id Episode id
 * @apiUse listParams
 * @apiSuccess {Object} User.
 */
router.put('/updateWatchlisted/:id',
  token({ required: true }),
  updateWatchlistedEpisodes)

/**
 * @api {put} /episodes/:id Update episode
 * @apiName UpdateEpisode
 * @apiGroup Episode
 * @apiPermission admin
 * @apiParam {String} access_token admin access token.
 * @apiParam name Episode's name.
 * @apiParam synopsis Episode's synopsis.
 * @apiParam airTime Episode's airTime.
 * @apiParam duration Episode's duration.
 * @apiSuccess {Object} episode Episode's data.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 * @apiError 404 Episode not found.
 * @apiError 401 admin access only.
 */
router.put('/:id',
  token({ required: true, roles: ['admin'] }),
  body({ name, synopsis, airTime, duration }),
  update)

/**
 * @api {delete} /episodes/:id Delete episode
 * @apiName DeleteEpisode
 * @apiGroup Episode
 * @apiPermission admin
 * @apiParam {String} access_token admin access token.
 * @apiSuccess (Success 204) 204 No Content.
 * @apiError 404 Episode not found.
 * @apiError 401 admin access only.
 */
router.delete('/:id',
  token({ required: true, roles: ['admin'] }),
  destroy)

export default router
