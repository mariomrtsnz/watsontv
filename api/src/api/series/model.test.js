import { Series } from '.'

let series

beforeEach(async () => {
  series = await Series.create({ broadcaster: 'test', seasons: 'test' })
})

describe('view', () => {
  it('returns simple view', () => {
    const view = series.view()
    expect(typeof view).toBe('object')
    expect(view.id).toBe(series.id)
    expect(view.broadcaster).toBe(series.broadcaster)
    expect(view.seasons).toBe(series.seasons)
    expect(view.createdAt).toBeTruthy()
    expect(view.updatedAt).toBeTruthy()
  })

  it('returns full view', () => {
    const view = series.view(true)
    expect(typeof view).toBe('object')
    expect(view.id).toBe(series.id)
    expect(view.broadcaster).toBe(series.broadcaster)
    expect(view.seasons).toBe(series.seasons)
    expect(view.createdAt).toBeTruthy()
    expect(view.updatedAt).toBeTruthy()
  })
})
