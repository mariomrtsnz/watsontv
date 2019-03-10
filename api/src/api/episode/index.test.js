import request from 'supertest'
import { apiRoot } from '../../config'
import { signSync } from '../../services/jwt'
import express from '../../services/express'
import { User } from '../user'
import routes, { Episode } from '.'

const app = () => express(apiRoot, routes)

let userSession, adminSession, episode

beforeEach(async () => {
  const user = await User.create({ email: 'a@a.com', password: '123456' })
  const admin = await User.create({ email: 'c@c.com', password: '123456', role: 'admin' })
  userSession = signSync(user.id)
  adminSession = signSync(admin.id)
  episode = await Episode.create({})
})

test('POST /episodes 201 (admin)', async () => {
  const { status, body } = await request(app())
    .post(`${apiRoot}`)
    .send({ access_token: adminSession, name: 'test', synopsis: 'test', airTime: 'test', duration: 'test' })
  expect(status).toBe(201)
  expect(typeof body).toEqual('object')
  expect(body.name).toEqual('test')
  expect(body.synopsis).toEqual('test')
  expect(body.airTime).toEqual('test')
  expect(body.duration).toEqual('test')
})

test('POST /episodes 401 (user)', async () => {
  const { status } = await request(app())
    .post(`${apiRoot}`)
    .send({ access_token: userSession })
  expect(status).toBe(401)
})

test('POST /episodes 401', async () => {
  const { status } = await request(app())
    .post(`${apiRoot}`)
  expect(status).toBe(401)
})

test('GET /episodes 200', async () => {
  const { status, body } = await request(app())
    .get(`${apiRoot}`)
  expect(status).toBe(200)
  expect(Array.isArray(body.rows)).toBe(true)
  expect(Number.isNaN(body.count)).toBe(false)
})

test('GET /episodes/:id 200', async () => {
  const { status, body } = await request(app())
    .get(`${apiRoot}/${episode.id}`)
  expect(status).toBe(200)
  expect(typeof body).toEqual('object')
  expect(body.id).toEqual(episode.id)
})

test('GET /episodes/:id 404', async () => {
  const { status } = await request(app())
    .get(apiRoot + '/123456789098765432123456')
  expect(status).toBe(404)
})

test('PUT /episodes/:id 200 (admin)', async () => {
  const { status, body } = await request(app())
    .put(`${apiRoot}/${episode.id}`)
    .send({ access_token: adminSession, name: 'test', synopsis: 'test', airTime: 'test', duration: 'test' })
  expect(status).toBe(200)
  expect(typeof body).toEqual('object')
  expect(body.id).toEqual(episode.id)
  expect(body.name).toEqual('test')
  expect(body.synopsis).toEqual('test')
  expect(body.airTime).toEqual('test')
  expect(body.duration).toEqual('test')
})

test('PUT /episodes/:id 401 (user)', async () => {
  const { status } = await request(app())
    .put(`${apiRoot}/${episode.id}`)
    .send({ access_token: userSession })
  expect(status).toBe(401)
})

test('PUT /episodes/:id 401', async () => {
  const { status } = await request(app())
    .put(`${apiRoot}/${episode.id}`)
  expect(status).toBe(401)
})

test('PUT /episodes/:id 404 (admin)', async () => {
  const { status } = await request(app())
    .put(apiRoot + '/123456789098765432123456')
    .send({ access_token: adminSession, name: 'test', synopsis: 'test', airTime: 'test', duration: 'test' })
  expect(status).toBe(404)
})

test('DELETE /episodes/:id 204 (admin)', async () => {
  const { status } = await request(app())
    .delete(`${apiRoot}/${episode.id}`)
    .query({ access_token: adminSession })
  expect(status).toBe(204)
})

test('DELETE /episodes/:id 401 (user)', async () => {
  const { status } = await request(app())
    .delete(`${apiRoot}/${episode.id}`)
    .query({ access_token: userSession })
  expect(status).toBe(401)
})

test('DELETE /episodes/:id 401', async () => {
  const { status } = await request(app())
    .delete(`${apiRoot}/${episode.id}`)
  expect(status).toBe(401)
})

test('DELETE /episodes/:id 404 (admin)', async () => {
  const { status } = await request(app())
    .delete(apiRoot + '/123456789098765432123456')
    .query({ access_token: adminSession })
  expect(status).toBe(404)
})
