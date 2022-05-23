package com.example.myapplication.renderer

import com.example.myapplication.renderer.elements.Content
import com.example.myapplication.renderer.elements.Kind

class KindRenderer {

    fun collectKinds(content: List<Content>): Map<String, Kind> {
        val map = mutableMapOf<String, Kind>()
        collectKinds(content, map)
        return map
    }

    private fun collectKinds(content: List<Content>, map: MutableMap<String, Kind>): MutableMap<String, Kind> {
        content.forEach { elem ->
            if (elem is Kind) {
                map[elem.kind] = elem
            }

            elem.children?.let {  children ->
                collectKinds(children, map)
            }
        }

        return map
    }
}