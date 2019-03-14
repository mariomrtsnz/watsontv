import mongoose, { Schema } from 'mongoose'

const photoSchema = new Schema({
  imgurlink: {
    type: String
  },
  deletehash: {
    type: String
  }
}, {
  timestamps: true,
  toJSON: {
    virtuals: true,
    transform: (obj, ret) => { delete ret._id }
  }
})

photoSchema.methods = {
  view (full) {
    const view = {
      // simple view
      id: this.id,
      imgurlink: this.imgurlink,
      deletehash: this.deletehash,
      createdAt: this.createdAt,
      updatedAt: this.updatedAt
    }

    return full ? {
      ...view
      // add properties for a full view
    } : view
  }
}

const model = mongoose.model('Photo', photoSchema)

export const schema = model.schema
export default model
