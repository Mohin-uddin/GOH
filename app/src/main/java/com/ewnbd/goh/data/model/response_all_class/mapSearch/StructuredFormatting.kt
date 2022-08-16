package com.ewnbd.goh.data.model.response_all_class.mapSearch

data class StructuredFormatting(
    val main_text: String,
    val main_text_matched_substrings: List<MainTextMatchedSubstring>,
    val secondary_text: String
)