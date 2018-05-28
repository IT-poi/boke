blog.controller('articleDetailController', function($rootScope, $scope, $http, $state, $stateParams, $uibModal) {
    $scope.articleId = $stateParams.id;
    $scope.article = {};
    if ($scope.articleId !== 'add') { //不是新增
        var url = $rootScope.baseUrl + "/api/0/home/article/"+$scope.articleId;
        $http.get(url).success(function (result) {
            if (result.status == 0) {
                $scope.article = result.data;
                $scope.articleType = $scope.article.type.toString();
                // testEditor.setMarkdown($scope.article.stringContent);
                initEditor();
            }
        });
    }else {
        initEditor();
    }


    $scope.open = function () {
        var modalInstance = $uibModal.open({
            templateUrl: 'modal/article-publish-modal.html',
            controller: 'ModalInstanceCtrl',
            size: 'md',
            resolve: {
                params: function () {
                    return "";
                }
            }
        });
        modalInstance.result.then(function (response) {
            console.log("ok...");
            $scope.selected = response;
            console.log($scope.selected);
        }, function () {
            console.log("close...");
        });
    };


    //发布文章
    $scope.publish = function () {
        var modalInstance = $uibModal.open({
            // animation: true,
            // ariaLabelledBy: 'modal-title',
            // ariaDescribedBy: 'modal-body',
            templateUrl: 'modal/article-publish-modal.html',
            controller: 'ModalInstanceCtrl',
            size: 'md',
            // appendTo: parentElem,
            resolve: {
                params: function () {
                    return {
                        "brief": $scope.testEditor.getMarkdown().substring(0, 100),
                        "htmlContent": $scope.testEditor.getHTML(),
                        "id": $scope.articleId === 'add' ? null : $scope.articleId,
                        "status": $scope.article.status,
                        "type": $scope.articleType,
                        "labelNames": $scope.article.labelNames,
                        "picUrl": $scope.article.picUrl,
                        "stringContent": $scope.testEditor.getMarkdown(),
                        "title": $scope.article.title,
                        "categoryId": $scope.article.categoryId
                    };
                }
            }
        });
        modalInstance.result.then(function (result) {
            $scope.article = result;
        }, function () {
            console.log("close...");
        });
    };

    //Ctrl+S 保存草稿
    $scope.save = function (html, markdown) {
        if (isBlank(html) || isBlank(markdown) || isBlank($scope.article.title)) {
            UIToastr.error("文章内容不能为空！");
            return;
        }
        console.log($scope.articleType);
        var param = {
            "brief": markdown.substring(0, 100),
            "htmlContent": html,
            "id": $scope.articleId === 'add' ? null : $scope.articleId,
            "status": $scope.article.status,
            "type": $scope.articleType,
            "stringContent": markdown,
            "title": $scope.article.title,
            "categoryId": $scope.article.categoryId,
            "labelNames": $scope.article.labelNames
        };
        $http.post($rootScope.baseUrl + "/api/1/manage/article/sou", JSON.stringify(param)).success(function (result) {
            if (result.status == 0) {
                UIToastr.success("保存文章成功！");
                $scope.article = result.data;
                if ($scope.articleId === 'add'){
                    $scope.articleId = result.data.id;
                }
            }
        });
    };


    function initEditor() {
        $scope.testEditor = editormd("test-editormd", {
            width: "100%",
            height: 740,
            path : 'plug/editor.md/lib/',
            theme        : (localStorage.theme) ? localStorage.theme : "dark",
            previewTheme : (localStorage.previewTheme) ? localStorage.previewTheme : "dark",
            editorTheme  : (localStorage.editorTheme) ? localStorage.editorTheme : "pastel-on-dark",
            markdown :  $scope.article.stringContent,
            codeFold : true,
            //syncScrolling : false,
            saveHTMLToTextarea : true,    // 保存 HTML 到 Textarea
            searchReplace : true,
            //watch : false,                // 关闭实时预览
            htmlDecode : "style,script,iframe|on*",            // 开启 HTML 标签解析，为了安全性，默认不开启
            //toolbar  : false,             //关闭工具栏
            //previewCodeHighlight : false, // 关闭预览 HTML 的代码块高亮，默认开启
            emoji : true,
            taskList : true,
            tocm            : true,         // Using [TOCM]
            tex : true,                   // 开启科学公式TeX语言支持，默认关闭
            flowChart : true,             // 开启流程图支持，默认关闭
            sequenceDiagram : true,       // 开启时序/序列图支持，默认关闭,
            //dialogLockScreen : false,   // 设置弹出层对话框不锁屏，全局通用，默认为true
            //dialogShowMask : false,     // 设置弹出层对话框显示透明遮罩层，全局通用，默认为true
            //dialogDraggable : false,    // 设置弹出层对话框不可拖动，全局通用，默认为true
            //dialogDraggable : false,    // 设置弹出层对话框不可拖动，全局通用，默认为true
            //dialogMaskOpacity : 0.4,    // 设置透明遮罩层的透明度，全局通用，默认值为0.1
            //dialogMaskBgColor : "#000", // 设置透明遮罩层的背景颜色，全局通用，默认为#fff
            imageUpload : true,
            imageFormats : ["jpg", "jpeg", "gif", "png", "bmp", "webp"],
            imageUploadURL : $rootScope.baseUrl + "/api/0/file/uploadPic",
            onload : function() {
                console.log('onload', this);
                var keyMap = {
                    "Ctrl-S": function(cm) {
                        $scope.save($scope.testEditor.getHTML(), $scope.testEditor.getMarkdown());
                    },
                    "Ctrl-A": function(cm) { // default Ctrl-A selectAll
                        // custom
                        alert("Ctrl+A");
                        cm.execCommand("selectAll");
                    }
                };

                // setting signle key
                var keyMap2 = {
                    "Ctrl-T": function(cm) {
                        alert("Ctrl+T");
                    }
                };

                this.addKeyMap(keyMap);
                this.addKeyMap(keyMap2);
                //this.fullscreen();
                //this.unwatch();
                //this.watch().fullscreen();

                //this.setMarkdown("#PHP");
                //this.width("100%");
                //this.height(480);
                //this.resize("100%", 640);
            }
        });

        $("#goto-line-btn").bind("click", function(){
            $scope.testEditor.gotoLine(90);
        });

        $("#show-btn").bind('click', function(){
            $scope.testEditor.show();
        });

        $("#hide-btn").bind('click', function(){
            $scope.testEditor.hide();
        });

        $("#get-md-btn").bind('click', function(){
            alert($scope.testEditor.getMarkdown());
        });

        $("#get-html-btn").bind('click', function() {
            alert($scope.testEditor.getHTML());
        });

        $("#watch-btn").bind('click', function() {
            $scope.testEditor.watch();
        });

        $("#unwatch-btn").bind('click', function() {
            $scope.testEditor.unwatch();
        });

        $("#preview-btn").bind('click', function() {
            $scope.testEditor.previewing();
        });

        $("#fullscreen-btn").bind('click', function() {
            $scope.testEditor.fullscreen();
        });

        $("#show-toolbar-btn").bind('click', function() {
            $scope.testEditor.showToolbar();
        });

        $("#close-toolbar-btn").bind('click', function() {
            $scope.testEditor.hideToolbar();
        });

        $("#toc-menu-btn").click(function(){
            $scope.testEditor.config({
                tocDropdown   : true,
                tocTitle      : "目录 Table of Contents",
            });
        });

        $("#toc-default-btn").click(function() {
            $scope.testEditor.config("tocDropdown", false);
        });


        themeSelect("editormd-theme-select", editormd.themes, "theme", function($this, theme) {
            $scope.testEditor.setTheme(theme);
        });

        themeSelect("editor-area-theme-select", editormd.editorThemes, "editorTheme", function($this, theme) {
            $scope.testEditor.setCodeMirrorTheme(theme);
            // or testEditor.setEditorTheme(theme);
        });

        themeSelect("preview-area-theme-select", editormd.previewThemes, "previewTheme", function($this, theme) {
            $scope.testEditor.setPreviewTheme(theme);
        });
    };

});

