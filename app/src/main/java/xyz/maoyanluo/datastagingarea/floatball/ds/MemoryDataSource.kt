package xyz.maoyanluo.datastagingarea.floatball.ds

import xyz.maoyanluo.datastagingarea.floatball.floatwindow.panel.rv.model.BaseModel

class MemoryDataSource: BaseDataSource() {

    private val dataList = ArrayList<BaseModel>()

    override fun getDataCount(): Int {
        return dataList.size
    }

    override fun getDataAt(pos: Int): BaseModel {
        return dataList[pos]
    }

    override fun addItem(model: BaseModel) {
        dataList.add(model)
    }

}