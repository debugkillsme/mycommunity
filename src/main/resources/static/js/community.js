//提交回复
function post() {
    var threadId = $("#thread_id").val();
    var content = $("#comment_content").val();
    comment2target(threadId, 1, content);
}

function comment2target(targetId, type, content) {
    if (!content) {
        alert("不能回复空内容");
        return;
    }
    $.ajax({
        type: "POST",
        //发送请求的地址
        url: "/comment",
        contentType: "application/json",
        data: JSON.stringify({
            "parentId": targetId,
            "content": content,
            "type": type
        }),
        //请求成功后的回调函数
        success: function (response) {
            if (response.code == 200) {
                window.location.reload();
            } else {
                if (response.code == 2003) {
                    let isAccepted = confirm(response.message);
                    //通过localStorage自动加载登录页面之后，通过closable变量判断自行关闭
                    if (isAccepted) {
                        window.open("https://github.com/login/oauth/authorize?client_id=84e956f4ba0c5dec2948&redirect_uri=http://localhost:8080/callback&scope=user&state=1");
                        //设置标志位，自动刷新页面
                        window.localStorage.setItem("closable", true);
                    } else {
                        alert(response.message);
                    }
                }
            }
        },
        dataType: "json"
    });
}

function comment(e) {
    var commentId = e.getAttribute("data-id");
    var content = $("#input-" + commentId).val();
    comment2target(commentId, 2, content);


}

//展开二级评论
function collapseComments(e) {
    //评论评论的id

    var id = e.getAttribute("data-id");
    //被评论的评论id
    var comments = $("#comment-" + id);

    //获取二级评论的展开状态,这里可以尝试用data-toggle做
    var collapse = e.getAttribute("data-collapse");
    if (collapse) {
        //折叠二级评论
        comments.removeClass("in");
        e.removeAttribute("data-collapse");
        e.classList.remove("active");
    } else {
        var subCommentContainer = $("#comment-" + id);
        debugger;
        if (subCommentContainer.children().length != 1) {
            //children()方法返回被选中元素的直接子元素，等于1说明还未请求过或者没有二级评论，只有评论输入一个子节点
            comments.addClass("in");
            e.setAttribute("data-collapse", "in");
            //给二级评论子列表添加active样式
            e.classList.add("active");
        } else {
            //function(data)处理请求成功后得到的数据,data为CommentDTO列表
            //展开二级评论,需要在此处获得数据,拼接js此处
            $.getJSON("/comment/" + id, function (data) {
                $.each(data.data.reverse(), function (index, comment) {
                    var mediaLeftElement = $("<div/>", {
                        "class": "media-left"
                    }).append($("<img/>", {
                        "class": "media-object img-rounded",
                        "src": comment.user.avatarUrl
                    }));

                    var mediaBodyElement = $("<div/>", {
                        "class": "media-body"
                    }).append($("<h5/>", {
                        "class": "media-heading",
                        "html": comment.user.name
                    })).append($("<div/>", {
                        "html": comment.content
                    })).append($("<div/>", {
                        "class": "menu"
                    }).append($("<span/>", {
                        "class": "pull-right",
                        "html": moment(comment.gmtCreate).format('YYYY-MM-DD')
                    })));

                    var mediaElement = $("<div/>", {
                        "class": "media"
                    }).append(mediaLeftElement).append(mediaBodyElement);

                    var commentElement = $("<div/>", {
                        "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 comments"
                    }).append(mediaElement);

                    subCommentContainer.prepend(commentElement);
                });
                //展开二级评论
                comments.addClass("in");
                // 标记二级评论展开状态
                e.setAttribute("data-collapse", "in");
                e.classList.add("active");
            });
        }

    }
}

function showSelectTag(){
    $("#select-tag").show();

}
function selectTag(e){
    var value = e.getAttribute("data-tag");
    //避免重复点击重复添加标签
    var previous = $("#tag").val();
    if(previous.indexOf(value)==-1){
        if(previous){
            $("#tag").val(previous+'/'+value);
        }else {
            $("#tag").val(value);
        }
    }

}