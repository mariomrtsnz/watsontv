import request from 'supertest'
import { apiRoot } from '../../config'
import { signSync } from '../../services/jwt'
import express from '../../services/express'
import { User } from '../user'
import routes, { Season } from '.'

const app = () => express(apiRoot, routes)

let userSession, adminSession, season

beforeEach(async () => {
  const user = await User.create({ email: 'a@a.com', password: '123456' })
  const admin = await User.create({ email: 'c@c.com', password: '123456', role: 'admin' })
  userSession = signSync(user.id)
  adminSession = signSync(admin.id)
  season = await Season.create({})
})

test('POST /seasons 201 (admin)', async () => {
  const { status, body } = await request(app())
    .post(`${apiRoot}`)
    .send({ access_token: adminSession, number: 'test', episodes: 'test' })
  expect(status).toBe(201)
  expect(typeof body).toEqual('object')
  expect(body.number).toEqual('test')
  expect(body.episodes).toEqual('test')
})

test('POST /seasons 401 (user)', async () => {
  const { status } = await request(app())
    .post(`${apiRoot}`)
    .send({ access_token: userSession })
  expect(status).toBe(401)
})

test('POST /seasons 401', async () => {
  const { status } = await request(app())
    .post(`${apiRoot}`)
  expect(status).toBe(401)
})

test('GET /seasons 200', async () => {
  const { status, body } = await request(app())
    .get(`${apiRoot}`)
  expect(status).toBe(200)
  expect(Array.isArray(body.rows)).toBe(true)
  expect(Number.isNaN(body.count)).toBe(false)
})

test('GET /seasons/:id 200', async () => {
  const { status, body } = await request(app())
    .get(`${apiRoot}/${season.id}`)
  expect(status).toBe(200)
  expect(typeof body).toEqual('object')
  expect(body.id).toEqual(season.id)
})

test('GET /seasons/:id 404', async () => {
  const { status } = await request(app())
    .get(apiRoot + '/123456789098765432123456')
  expect(status).toBe(404)
})

test('PUT /seasons/:id 200 (admin)', async () => {
  const { status, body } = await request(app())
    .put(`${apiRoot}/${season.id}`)
    .send({ access_token: adminSession, number: 'test', episodes: 'test' })
  expect(status).toBe(200)
  expect(typeof body).toEqual('object')
  expect(body.id).toEqual(season.id)
  expect(body.number).toEqual('test')
  expect(body.episodes).toEqual('test')
})

test('PUT /seasons/:id 401 (user)', async () => {
  const { status } = await request(app())
    .put(`${apiRoot}/${season.id}`)
    .send({ access_token: userSession })
  expect(status).toBe(401)
})

test('PUT /seasons/:id 401', async () => {
  const { status } = await request(app())
    .put(`${apiRoot}/${season.id}`)
  expect(status).toBe(401)
})

test('PUT /seasons/:id 404 (admin)', async () => {
  const { status } = await request(app())
    .put(apiRoot + '/123456789098765432123456')
    .send({ access_token: adminSession, number: 'test', episodes: 'test' })
  expect(status).toBe(404)
})

test('DELETE /seasons/:id 204 (admin)', async () => {
  const { status } = await request(app())
    .delete(`${apiRoot}/${season.id}`)
    .query({ access_token: adminSession })
  expect(status).toBe(204)
})

test('DELETE /seasons/:id 401 (user)', async () => {
  const { status } = await request(app())
    .delete(`${apiRoot}/${season.id}`)
    .query({ access_token: userSession })
  expect(status).toBe(401)
})

test('DELETE /seasons/:id 401', async () => {
  const { status } = await request(app())
    .delete(`${apiRoot}/${season.id}`)
  expect(status).toBe(401)
})

test('DELETE /seasons/:id 404 (admin)', async () => {
  const { status } = await request(app())
    .delete(apiRoot + '/123456789098765432123456')
    .query({ access_token: adminSession })
  expect(status).toBe(404)
})
