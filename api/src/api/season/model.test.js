import { Season } from '.'

let season

beforeEach(async () => {
  season = await Season.create({ number: 'test', episodes: 'test' })
})

describe('view', () => {
  it('returns simple view', () => {
    const view = season.view()
    expect(typeof view).toBe('object')
    expect(view.id).toBe(season.id)
    expect(view.number).toBe(season.number)
    expect(view.episodes).toBe(season.episodes)
    expect(view.createdAt).toBeTruthy()
    expect(view.updatedAt).toBeTruthy()
  })

  it('returns full view', () => {
    const view = season.view(true)
    expect(typeof view).toBe('object')
    expect(view.id).toBe(season.id)
    expect(view.number).toBe(season.number)
    expect(view.episodes).toBe(season.episodes)
    expect(view.createdAt).toBeTruthy()
    expect(view.updatedAt).toBeTruthy()
  })
})
