package com.common.jpa.entity

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.io.Serializable

@Embeddable
data class AttachFileId(
    @Column(name = "attach_file_id")
    val attachFileId: Long = 0,

    @Column(name = "attach_file_no")
    val attachFileNo: Long = 0,
) : Serializable

@Entity
@Table(name = "attach_file")
class AttachFile(

    @EmbeddedId
    val id: AttachFileId,

    @Column(name = "original_file_name")
    var originalFileName: String,

    @Column(name = "file_path")
    var filePath: String,

    @Column(name = "size")
    var size: Long,

    @Column(name = "extension")
    var extension: String,

    @Column(name = "is_deleted")
    var isDeleted: Boolean = false,

) : BaseEntity()
