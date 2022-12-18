package xyz.maoyanluo.datastagingarea.floatball.ds

import xyz.maoyanluo.datastagingarea.floatball.model.BaseModel

abstract class BaseDataSource {

    abstract fun getDataCount(): Int
    abstract fun getDataAt(pos: Int): BaseModel
    abstract fun addItem(model: BaseModel)

}