package com.zjf.clear.ui.state

/**
 * create by colin
 * 2022/8/15
 */
data class ResultItemUiState(
    val id: Int,
    val icon: Int,
    val displayName: String,
    val descriptor: String,
    val buttonText: String,
    val jumpClass: Class<*>
)
