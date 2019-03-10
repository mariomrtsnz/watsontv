import { Movie } from '.'

let movie

beforeEach(async () => {
  movie = await Movie.create({})
})

describe('view', () => {
  it('returns simple view', () => {
    const view = movie.view()
    expect(typeof view).toBe('object')
    expect(view.id).toBe(movie.id)
    expect(view.createdAt).toBeTruthy()
    expect(view.updatedAt).toBeTruthy()
  })

  it('returns full view', () => {
    const view = movie.view(true)
    expect(typeof view).toBe('object')
    expect(view.id).toBe(movie.id)
    expect(view.createdAt).toBeTruthy()
    expect(view.updatedAt).toBeTruthy()
  })
})
