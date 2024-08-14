package wangyeo.interview.core.models

import wangyeo.interview.core.enums.TagType

data class Tag(
    val name: TagType,
    val message: String?,
)
