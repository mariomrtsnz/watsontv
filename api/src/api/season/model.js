import mongoose, { Schema } from 'mongoose'

const seasonSchema = new Schema({
  series: {
    type: Schema.Types.ObjectId,
    ref: 'Series',
    required: true
  },
  number: {
    type: Number,
    min: 1,
    required: true
  },
  episodes: {
    type: Schema.Types.ObjectId,
    ref: 'Episode'
  }
}, {
  strict: false,
  timestamps: true,
  toJSON: {
    virtuals: true,
    transform: (obj, ret) => { delete ret._id }
  }
})

seasonSchema.methods = {
  view (full) {
    const view = {
      // simple view
      id: this.id,
      series: this.series,
      number: this.number,
      episodes: this.episodes,
      createdAt: this.createdAt,
      updatedAt: this.updatedAt
    }

    return full ? {
      ...view
      // add properties for a full view
    } : view
  }
}

const model = mongoose.model('Season', seasonSchema)

export const schema = model.schema
export default model
