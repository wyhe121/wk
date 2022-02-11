package com.example.projectdemo.data.model.bean

import com.example.projectdemo.mInterface.DaggerManComponent
import javax.inject.Inject

class Man {
    @Inject
    lateinit var car:Car

    constructor()
    {
        DaggerManComponent.create().injectMan(this);
        car.log1()
    }

}