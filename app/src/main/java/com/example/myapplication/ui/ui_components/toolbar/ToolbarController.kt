package com.example.myapplication.ui.ui_components.toolbar

import androidx.compose.runtime.Stable

@Stable
interface ToolbarController {

    fun getToolbarActions(route: String): List<ToolbarAction>

    fun setToolbarActions(route: String, actions: List<ToolbarAction>)
}