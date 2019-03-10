import request from 'supertest'
import { apiRoot } from '../../config'
import express from '../../services/express'
import routes, { Collection } from '.'

const app = () => express(apiRoot, routes)

let collection

beforeEach(async () => {
  collection = await Collection.create({})
})

test('POST /collections 201', async () => {
  const { status, body } = await request(app())
    .post(`${apiRoot}`)
    .send({ name: 'test', description: 'test', collected: 'test' })
  expect(status).toBe(201)
  expect(typeof body).toEqual('object')
  expect(body.name).toEqual('test')
  expect(body.description).toEqual('test')
  expect(body.collected).toEqual('test')
})

test('GET /collections 200', async () => {
  const { status, body } = await request(app())
    .get(`${apiRoot}`)
  expect(status).toBe(200)
  expect(Array.isArray(body.rows)).toBe(true)
  expect(Number.isNaN(body.count)).toBe(false)
})

test('GET /collections/:id 200', async () => {
  const { status, body } = await request(app())
    .get(`${apiRoot}/${collection.id}`)
  expect(status).toBe(200)
  expect(typeof body).toEqual('object')
  expect(body.id).toEqual(collection.id)
})

test('GET /collections/:id 404', async () => {
  const { status } = await request(app())
    .get(apiRoot + '/123456789098765432123456')
  expect(status).toBe(404)
})

test('PUT /collections/:id 200', async () => {
  const { status, body } = await request(app())
    .put(`${apiRoot}/${collection.id}`)
    .send({ name: 'test', description: 'test', collected: 'test' })
  expect(status).toBe(200)
  expect(typeof body).toEqual('object')
  expect(body.id).toEqual(collection.id)
  expect(body.name).toEqual('test')
  expect(body.description).toEqual('test')
  expect(body.collected).toEqual('test')
})

test('PUT /collections/:id 404', async () => {
  const { status } = await request(app())
    .put(apiRoot + '/123456789098765432123456')
    .send({ name: 'test', description: 'test', collected: 'test' })
  expect(status).toBe(404)
})

test('DELETE /collections/:id 204', async () => {
  const { status } = await request(app())
    .delete(`${apiRoot}/${collection.id}`)
  expect(status).toBe(204)
})

test('DELETE /collections/:id 404', async () => {
  const { status } = await request(app())
    .delete(apiRoot + '/123456789098765432123456')
  expect(status).toBe(404)
})
