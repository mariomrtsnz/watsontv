import mongoose, { Schema } from 'mongoose'
import { Media } from '../media';

const seriesSchema = new Schema({
  broadcaster: {
    type: String
  },
  seasons: [{
    type: Schema.Types.ObjectId,
    ref: 'Season'
  }],
  airsDayOfWeek: {
    type: Number,
    min: 1,
    max: 7,
    required: true
  }
}, {
  strict: false,
  timestamps: true,
  toJSON: {
    virtuals: true,
    transform: (obj, ret) => { delete ret._id }
  }
});

seriesSchema.methods = {
  view (full) {
    const view = {
      // simple view
      id: this.id,
      title: this.title,
      releaseDate: this.releaseDate,
      rating: this.rating,
      cast: this.cast,
      coverImage: this.coverImage,
      genre: this.genre,
      synopsis: this.synopsis,
      mediaType: this.__t,
      broadcaster: this.broadcaster,
      seasons: this.seasons,
      airsDayOfWeek: this.airsDayOfWeek,
      createdAt: this.createdAt,
      updatedAt: this.updatedAt
    }

    return full ? {
      ...view
      // add properties for a full view
    } : view
  }
}

const model = Media.discriminator('Series', seriesSchema)

export const schema = model.schema
export default model
