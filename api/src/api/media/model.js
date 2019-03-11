import mongoose, { Schema } from 'mongoose'

const baseOptions = {
  discriminatorKey: 'mediaType', // our discriminator key, could be anything
  collection: 'items', // the name of our collection
};

const mediaSchema = new Schema({
  title: {
    type: String,
    required: true
  },
  releaseDate: {
    type: Date
  },
  rating: [{
    type: Number
  }],
  cast: [{
    type: Schema.Types.ObjectId,
    ref: 'Actor'
  }],
  coverImage: {
    type: String,
    required: true
  },
  genre: {
    type: Schema.Types.ObjectId,
    ref: 'Genre',
    required: true
  },
  synopsis: {
    type: String,
    required: true
  }
}, {
  baseOptions,
  strict: false,
  timestamps: true,
  toJSON: {
    virtuals: true,
    transform: (obj, ret) => { delete ret._id }
  }
})

mediaSchema.methods = {
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
      mediaType: this.__t,
      synopsis: this.synopsis,
      createdAt: this.createdAt,
      updatedAt: this.updatedAt
    }

    return full ? {
      ...view
      // add properties for a full view
    } : view
  }
}

const model = mongoose.model('Media', mediaSchema)

export const schema = model.schema
export default model
