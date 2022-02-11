package com.example.projectdemo.mInterface

import com.example.projectdemo.data.model.bean.Man
import dagger.Component


@Component
interface ManComponent {
    fun injectMan(man: Man?)
}