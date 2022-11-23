"use strict";
(self["webpackChunkmicroblogging"] = self["webpackChunkmicroblogging"] || []).push([["main"],{

/***/ 158:
/*!***************************************!*\
  !*** ./src/app/app-routing.module.ts ***!
  \***************************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

__webpack_require__.r(__webpack_exports__);
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   "AppRoutingModule": () => (/* binding */ AppRoutingModule)
/* harmony export */ });
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @angular/router */ 124);
/* harmony import */ var _home_screen_home_screen_component__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./home-screen/home-screen.component */ 4087);
/* harmony import */ var _mb_viewer_mb_viewer_component__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ./mb-viewer/mb-viewer.component */ 5671);
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/core */ 2560);





const routes = [{
  path: '',
  title: 'Home',
  component: _home_screen_home_screen_component__WEBPACK_IMPORTED_MODULE_0__.HomeScreenComponent
}, {
  path: 'post/:id/view',
  component: _mb_viewer_mb_viewer_component__WEBPACK_IMPORTED_MODULE_1__.MbViewerComponent
}];
class AppRoutingModule {
  static #_ = this.ɵfac = function AppRoutingModule_Factory(t) {
    return new (t || AppRoutingModule)();
  };
  static #_2 = this.ɵmod = /*@__PURE__*/_angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵdefineNgModule"]({
    type: AppRoutingModule
  });
  static #_3 = this.ɵinj = /*@__PURE__*/_angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵdefineInjector"]({
    imports: [_angular_router__WEBPACK_IMPORTED_MODULE_3__.RouterModule.forRoot(routes), _angular_router__WEBPACK_IMPORTED_MODULE_3__.RouterModule]
  });
}
(function () {
  (typeof ngJitMode === "undefined" || ngJitMode) && _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵsetNgModuleScope"](AppRoutingModule, {
    imports: [_angular_router__WEBPACK_IMPORTED_MODULE_3__.RouterModule],
    exports: [_angular_router__WEBPACK_IMPORTED_MODULE_3__.RouterModule]
  });
})();

/***/ }),

/***/ 5041:
/*!**********************************!*\
  !*** ./src/app/app.component.ts ***!
  \**********************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

__webpack_require__.r(__webpack_exports__);
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   "AppComponent": () => (/* binding */ AppComponent)
/* harmony export */ });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ 2560);
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/router */ 124);


class AppComponent {
  constructor() {
    this.title = 'microblogging';
  }
  static #_ = this.ɵfac = function AppComponent_Factory(t) {
    return new (t || AppComponent)();
  };
  static #_2 = this.ɵcmp = /*@__PURE__*/_angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵdefineComponent"]({
    type: AppComponent,
    selectors: [["app-root"]],
    decls: 3,
    vars: 1,
    template: function AppComponent_Template(rf, ctx) {
      if (rf & 1) {
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementStart"](0, "h1");
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵtext"](1);
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementEnd"]();
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelement"](2, "router-outlet");
      }
      if (rf & 2) {
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵadvance"](1);
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵtextInterpolate"](ctx.title);
      }
    },
    dependencies: [_angular_router__WEBPACK_IMPORTED_MODULE_1__.RouterOutlet],
    styles: ["h1[_ngcontent-%COMP%] {\n  text-align: center;\n}\n/*# sourceURL=webpack://./src/app/app.component.less */\n/*# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbIndlYnBhY2s6Ly8uL3NyYy9hcHAvYXBwLmNvbXBvbmVudC5sZXNzIl0sIm5hbWVzIjpbXSwibWFwcGluZ3MiOiJBQUFBO0VBQ0ksa0JBQUE7QUFDSiIsInNvdXJjZXNDb250ZW50IjpbImgxIHtcbiAgICB0ZXh0LWFsaWduOiBjZW50ZXI7XG59Il0sInNvdXJjZVJvb3QiOiIifQ== */"]
  });
}

/***/ }),

/***/ 6747:
/*!*******************************!*\
  !*** ./src/app/app.module.ts ***!
  \*******************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

__webpack_require__.r(__webpack_exports__);
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   "AppModule": () => (/* binding */ AppModule)
/* harmony export */ });
/* harmony import */ var _angular_platform_browser__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(/*! @angular/platform-browser */ 4497);
/* harmony import */ var _angular_common_http__WEBPACK_IMPORTED_MODULE_9__ = __webpack_require__(/*! @angular/common/http */ 8987);
/* harmony import */ var _app_routing_module__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./app-routing.module */ 158);
/* harmony import */ var _app_component__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ./app.component */ 5041);
/* harmony import */ var _mb_editor_mb_editor_component__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./mb-editor/mb-editor.component */ 999);
/* harmony import */ var _mb_timeline_mb_timeline_component__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ./mb-timeline/mb-timeline.component */ 954);
/* harmony import */ var _mb_viewer_mb_viewer_component__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ./mb-viewer/mb-viewer.component */ 5671);
/* harmony import */ var _home_screen_home_screen_component__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! ./home-screen/home-screen.component */ 4087);
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_8__ = __webpack_require__(/*! @angular/forms */ 2508);
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! @angular/core */ 2560);










class AppModule {
  static #_ = this.ɵfac = function AppModule_Factory(t) {
    return new (t || AppModule)();
  };
  static #_2 = this.ɵmod = /*@__PURE__*/_angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵdefineNgModule"]({
    type: AppModule,
    bootstrap: [_app_component__WEBPACK_IMPORTED_MODULE_1__.AppComponent]
  });
  static #_3 = this.ɵinj = /*@__PURE__*/_angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵdefineInjector"]({
    imports: [_angular_platform_browser__WEBPACK_IMPORTED_MODULE_7__.BrowserModule, _angular_forms__WEBPACK_IMPORTED_MODULE_8__.FormsModule, _angular_common_http__WEBPACK_IMPORTED_MODULE_9__.HttpClientModule, _app_routing_module__WEBPACK_IMPORTED_MODULE_0__.AppRoutingModule]
  });
}
(function () {
  (typeof ngJitMode === "undefined" || ngJitMode) && _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵsetNgModuleScope"](AppModule, {
    declarations: [_app_component__WEBPACK_IMPORTED_MODULE_1__.AppComponent, _mb_editor_mb_editor_component__WEBPACK_IMPORTED_MODULE_2__.MbEditorComponent, _mb_timeline_mb_timeline_component__WEBPACK_IMPORTED_MODULE_3__.MbTimelineComponent, _mb_viewer_mb_viewer_component__WEBPACK_IMPORTED_MODULE_4__.MbViewerComponent, _home_screen_home_screen_component__WEBPACK_IMPORTED_MODULE_5__.HomeScreenComponent],
    imports: [_angular_platform_browser__WEBPACK_IMPORTED_MODULE_7__.BrowserModule, _angular_forms__WEBPACK_IMPORTED_MODULE_8__.FormsModule, _angular_common_http__WEBPACK_IMPORTED_MODULE_9__.HttpClientModule, _app_routing_module__WEBPACK_IMPORTED_MODULE_0__.AppRoutingModule]
  });
})();

/***/ }),

/***/ 4087:
/*!******************************************************!*\
  !*** ./src/app/home-screen/home-screen.component.ts ***!
  \******************************************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

__webpack_require__.r(__webpack_exports__);
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   "HomeScreenComponent": () => (/* binding */ HomeScreenComponent)
/* harmony export */ });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/core */ 2560);
/* harmony import */ var _mb_editor_mb_editor_component__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ../mb-editor/mb-editor.component */ 999);
/* harmony import */ var _mb_timeline_mb_timeline_component__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ../mb-timeline/mb-timeline.component */ 954);



class HomeScreenComponent {
  static #_ = this.ɵfac = function HomeScreenComponent_Factory(t) {
    return new (t || HomeScreenComponent)();
  };
  static #_2 = this.ɵcmp = /*@__PURE__*/_angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵdefineComponent"]({
    type: HomeScreenComponent,
    selectors: [["app-home-screen"]],
    decls: 2,
    vars: 0,
    template: function HomeScreenComponent_Template(rf, ctx) {
      if (rf & 1) {
        _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵelement"](0, "app-mb-editor")(1, "app-mb-timeline");
      }
    },
    dependencies: [_mb_editor_mb_editor_component__WEBPACK_IMPORTED_MODULE_0__.MbEditorComponent, _mb_timeline_mb_timeline_component__WEBPACK_IMPORTED_MODULE_1__.MbTimelineComponent],
    styles: ["\n/*# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IiIsInNvdXJjZVJvb3QiOiIifQ== */"]
  });
}

/***/ }),

/***/ 999:
/*!**************************************************!*\
  !*** ./src/app/mb-editor/mb-editor.component.ts ***!
  \**************************************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

__webpack_require__.r(__webpack_exports__);
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   "MbEditorComponent": () => (/* binding */ MbEditorComponent)
/* harmony export */ });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ 2560);
/* harmony import */ var _post_service__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ../post.service */ 8290);
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/forms */ 2508);



class MbEditorComponent {
  constructor(postService) {
    this.postService = postService;
    this.content = "";
  }
  create() {
    this.postService.create({
      content: this.content
    }).subscribe(post => {
      console.log("Post created", post);
      this.content = "";
    });
  }
  static #_ = this.ɵfac = function MbEditorComponent_Factory(t) {
    return new (t || MbEditorComponent)(_angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵdirectiveInject"](_post_service__WEBPACK_IMPORTED_MODULE_0__.PostService));
  };
  static #_2 = this.ɵcmp = /*@__PURE__*/_angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵdefineComponent"]({
    type: MbEditorComponent,
    selectors: [["app-mb-editor"]],
    decls: 10,
    vars: 2,
    consts: [[1, "editor-root"], [1, "editor", 3, "ngModel", "ngModelChange"], [1, "content-length"], [1, "btns"], [1, "btn-create", 3, "click"]],
    template: function MbEditorComponent_Template(rf, ctx) {
      if (rf & 1) {
        _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵelementStart"](0, "div", 0)(1, "div")(2, "div")(3, "textarea", 1);
        _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵlistener"]("ngModelChange", function MbEditorComponent_Template_textarea_ngModelChange_3_listener($event) {
          return ctx.content = $event;
        });
        _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵelementEnd"]()()();
        _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵelementStart"](4, "div", 2);
        _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵtext"](5);
        _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵelementEnd"]();
        _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵelementStart"](6, "div")(7, "div", 3)(8, "button", 4);
        _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵlistener"]("click", function MbEditorComponent_Template_button_click_8_listener() {
          return ctx.create();
        });
        _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵtext"](9, "Post");
        _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵelementEnd"]()()()();
      }
      if (rf & 2) {
        _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵadvance"](3);
        _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵproperty"]("ngModel", ctx.content);
        _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵadvance"](2);
        _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵtextInterpolate1"](" ", ctx.content.length, " ");
      }
    },
    dependencies: [_angular_forms__WEBPACK_IMPORTED_MODULE_2__.DefaultValueAccessor, _angular_forms__WEBPACK_IMPORTED_MODULE_2__.NgControlStatus, _angular_forms__WEBPACK_IMPORTED_MODULE_2__.NgModel],
    styles: [".editor-root[_ngcontent-%COMP%] {\n  display: table;\n  width: 100%;\n  margin-bottom: 2em;\n}\n.editor-root[_ngcontent-%COMP%]    > div[_ngcontent-%COMP%] {\n  display: table-row;\n}\n.editor-root[_ngcontent-%COMP%]    > div[_ngcontent-%COMP%]    > div[_ngcontent-%COMP%] {\n  display: table-cell;\n}\n.editor-root[_ngcontent-%COMP%]   .btns[_ngcontent-%COMP%] {\n  text-align: right;\n}\n.editor-root[_ngcontent-%COMP%]   .content-length[_ngcontent-%COMP%] {\n  text-align: right;\n  line-height: 2em;\n  font-style: italic;\n}\n.editor[_ngcontent-%COMP%] {\n  width: 100%;\n  box-sizing: border-box;\n  border: none;\n  border-radius: 5px;\n  background: #D0CBC8;\n  padding: 1em;\n}\n/*# sourceURL=webpack://./src/app/mb-editor/mb-editor.component.less */\n/*# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbIndlYnBhY2s6Ly8uL3NyYy9hcHAvbWItZWRpdG9yL21iLWVkaXRvci5jb21wb25lbnQubGVzcyJdLCJuYW1lcyI6W10sIm1hcHBpbmdzIjoiQUFBQTtFQUNJLGNBQUE7RUFDQSxXQUFBO0VBQ0Esa0JBQUE7QUFDSjtBQUpBO0VBTVEsa0JBQUE7QUFDUjtBQVBBO0VBU1ksbUJBQUE7QUFDWjtBQVZBO0VBY1EsaUJBQUE7QUFEUjtBQWJBO0VBa0JRLGlCQUFBO0VBQ0EsZ0JBQUE7RUFDQSxrQkFBQTtBQUZSO0FBTUE7RUFDSSxXQUFBO0VBQ0Esc0JBQUE7RUFDQSxZQUFBO0VBQ0Esa0JBQUE7RUFDQSxtQkFBQTtFQUNBLFlBQUE7QUFKSiIsInNvdXJjZXNDb250ZW50IjpbIi5lZGl0b3Itcm9vdCB7XG4gICAgZGlzcGxheTogdGFibGU7XG4gICAgd2lkdGg6IDEwMCU7XG4gICAgbWFyZ2luLWJvdHRvbTogMmVtO1xuXG4gICAgPmRpdiB7XG4gICAgICAgIGRpc3BsYXk6IHRhYmxlLXJvdztcblxuICAgICAgICA+ZGl2IHtcbiAgICAgICAgICAgIGRpc3BsYXk6IHRhYmxlLWNlbGw7XG4gICAgICAgIH1cbiAgICB9XG5cbiAgICAuYnRucyB7XG4gICAgICAgIHRleHQtYWxpZ246IHJpZ2h0O1xuICAgIH1cblxuICAgIC5jb250ZW50LWxlbmd0aCB7XG4gICAgICAgIHRleHQtYWxpZ246IHJpZ2h0O1xuICAgICAgICBsaW5lLWhlaWdodDogMmVtO1xuICAgICAgICBmb250LXN0eWxlOiBpdGFsaWM7XG4gICAgfVxufVxuXG4uZWRpdG9yIHtcbiAgICB3aWR0aDogMTAwJTtcbiAgICBib3gtc2l6aW5nOiBib3JkZXItYm94O1xuICAgIGJvcmRlcjogbm9uZTtcbiAgICBib3JkZXItcmFkaXVzOiA1cHg7XG4gICAgYmFja2dyb3VuZDogI0QwQ0JDODtcbiAgICBwYWRkaW5nOiAxZW07XG59Il0sInNvdXJjZVJvb3QiOiIifQ== */"]
  });
}

/***/ }),

/***/ 954:
/*!******************************************************!*\
  !*** ./src/app/mb-timeline/mb-timeline.component.ts ***!
  \******************************************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

__webpack_require__.r(__webpack_exports__);
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   "MbTimelineComponent": () => (/* binding */ MbTimelineComponent)
/* harmony export */ });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ 2560);
/* harmony import */ var _post_service__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ../post.service */ 8290);
/* harmony import */ var _angular_common__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/common */ 4666);
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @angular/router */ 124);




const _c0 = function (a1) {
  return ["post", a1, "view"];
};
function MbTimelineComponent_div_2_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵelementStart"](0, "div", 3)(1, "div", 4);
    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵtext"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵelementEnd"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵelementStart"](3, "div", 5);
    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵtext"](4);
    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵpipe"](5, "date");
    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    const post_r1 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵproperty"]("routerLink", _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵpureFunction1"](6, _c0, post_r1.id));
    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵadvance"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵtextInterpolate1"](" ", post_r1.content, " ");
    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵadvance"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵtextInterpolate1"](" ", _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵpipeBind2"](5, 3, post_r1.createdAt, "short"), " ");
  }
}
class MbTimelineComponent {
  constructor(postService) {
    this.postService = postService;
    this.posts = [];
    this.hasMore = true;
    this.loadingItems = false;
  }
  ngOnInit() {
    if (!this.loadingItems) {
      this.loadingItems = true;
      this.postService.getPosts().subscribe(page => {
        this.posts.push(...page.items);
        this.hasMore = page.items.length > 0;
        this.loadingItems = false;
      });
    }
  }
  onScroll(event) {
    if (this.hasMore && !this.loadingItems && event.target instanceof Document) {
      var scrollTop = typeof event.target.scrollingElement?.scrollTop == 'number' ? event.target.scrollingElement.scrollTop : 0;
      var clientHeight = typeof event.target.scrollingElement?.clientHeight == 'number' ? event.target.scrollingElement.clientHeight : 0;
      var scrollHeight = typeof event.target.scrollingElement?.scrollHeight == 'number' ? event.target.scrollingElement.scrollHeight : 0;
      if (scrollTop + clientHeight > 0.85 * scrollHeight) {
        this.loadingItems = true;
        this.postService.getPosts(this.posts.length).subscribe(page => {
          this.posts.push(...page.items);
          this.hasMore = page.items.length > 0;
          this.loadingItems = false;
        });
      }
    }
  }
  static #_ = this.ɵfac = function MbTimelineComponent_Factory(t) {
    return new (t || MbTimelineComponent)(_angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵdirectiveInject"](_post_service__WEBPACK_IMPORTED_MODULE_0__.PostService));
  };
  static #_2 = this.ɵcmp = /*@__PURE__*/_angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵdefineComponent"]({
    type: MbTimelineComponent,
    selectors: [["app-mb-timeline"]],
    hostBindings: function MbTimelineComponent_HostBindings(rf, ctx) {
      if (rf & 1) {
        _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵlistener"]("scroll", function MbTimelineComponent_scroll_HostBindingHandler($event) {
          return ctx.onScroll($event);
        }, false, _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵresolveWindow"]);
      }
    },
    decls: 3,
    vars: 1,
    consts: [[1, "root"], [1, "panel"], ["class", "post", 3, "routerLink", 4, "ngFor", "ngForOf"], [1, "post", 3, "routerLink"], [1, "content"], [1, "date"]],
    template: function MbTimelineComponent_Template(rf, ctx) {
      if (rf & 1) {
        _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵelementStart"](0, "div", 0)(1, "div", 1);
        _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵtemplate"](2, MbTimelineComponent_div_2_Template, 6, 8, "div", 2);
        _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵelementEnd"]()();
      }
      if (rf & 2) {
        _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵadvance"](2);
        _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵproperty"]("ngForOf", ctx.posts);
      }
    },
    dependencies: [_angular_common__WEBPACK_IMPORTED_MODULE_2__.NgForOf, _angular_router__WEBPACK_IMPORTED_MODULE_3__.RouterLink, _angular_common__WEBPACK_IMPORTED_MODULE_2__.DatePipe],
    styles: [".post[_ngcontent-%COMP%] {\n  background: #49306B;\n  color: #FFF;\n  padding: 1em;\n  margin: 0 0 0.7em 0;\n}\n.post[_ngcontent-%COMP%]   .content[_ngcontent-%COMP%] {\n  font-size: 1.2em;\n}\n.post[_ngcontent-%COMP%]   .date[_ngcontent-%COMP%] {\n  text-align: right;\n  font-size: 0.8em;\n}\n/*# sourceURL=webpack://./src/app/mb-timeline/mb-timeline.component.less */\n/*# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbIndlYnBhY2s6Ly8uL3NyYy9hcHAvbWItdGltZWxpbmUvbWItdGltZWxpbmUuY29tcG9uZW50Lmxlc3MiXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IkFBQUE7RUFDSSxtQkFBQTtFQUNBLFdBQUE7RUFDQSxZQUFBO0VBQ0EsbUJBQUE7QUFDSjtBQUxBO0VBT1EsZ0JBQUE7QUFDUjtBQVJBO0VBV1EsaUJBQUE7RUFDQSxnQkFBQTtBQUFSIiwic291cmNlc0NvbnRlbnQiOlsiLnBvc3Qge1xuICAgIGJhY2tncm91bmQ6ICM0OTMwNkI7XG4gICAgY29sb3I6ICNGRkY7XG4gICAgcGFkZGluZzogMWVtO1xuICAgIG1hcmdpbjogMCAwIDAuN2VtIDA7XG5cbiAgICAuY29udGVudCB7XG4gICAgICAgIGZvbnQtc2l6ZTogMS4yZW07XG4gICAgfVxuXG4gICAgLmRhdGUge1xuICAgICAgICB0ZXh0LWFsaWduOiByaWdodDtcbiAgICAgICAgZm9udC1zaXplOiAwLjhlbTtcbiAgICB9XG59Il0sInNvdXJjZVJvb3QiOiIifQ== */"]
  });
}

/***/ }),

/***/ 5671:
/*!**************************************************!*\
  !*** ./src/app/mb-viewer/mb-viewer.component.ts ***!
  \**************************************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

__webpack_require__.r(__webpack_exports__);
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   "MbViewerComponent": () => (/* binding */ MbViewerComponent)
/* harmony export */ });
/* harmony import */ var rxjs__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! rxjs */ 2673);
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/core */ 2560);
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @angular/router */ 124);
/* harmony import */ var _post_service__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ../post.service */ 8290);
/* harmony import */ var _angular_common__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! @angular/common */ 4666);





class MbViewerComponent {
  constructor(route, router, postService) {
    this.route = route;
    this.router = router;
    this.postService = postService;
    this.post = {
      id: 0,
      content: "",
      createdAt: new Date()
    };
  }
  ngOnInit() {
    this.route.paramMap.pipe((0,rxjs__WEBPACK_IMPORTED_MODULE_1__.switchMap)(params => this.postService.getPost(Number.parseInt(params.get('id'))))).subscribe(post => this.post = post);
  }
  delete() {
    this.postService.delete(this.post).subscribe({
      next: post => {
        console.log(post);
        this.router.navigate(['/']);
      },
      error: error => {
        console.log(error);
        this.router.navigate(['/']);
      }
    });
  }
  static #_ = this.ɵfac = function MbViewerComponent_Factory(t) {
    return new (t || MbViewerComponent)(_angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵdirectiveInject"](_angular_router__WEBPACK_IMPORTED_MODULE_3__.ActivatedRoute), _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵdirectiveInject"](_angular_router__WEBPACK_IMPORTED_MODULE_3__.Router), _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵdirectiveInject"](_post_service__WEBPACK_IMPORTED_MODULE_0__.PostService));
  };
  static #_2 = this.ɵcmp = /*@__PURE__*/_angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵdefineComponent"]({
    type: MbViewerComponent,
    selectors: [["app-mb-viewer"]],
    decls: 8,
    vars: 5,
    consts: [[1, "post"], [1, "btn-delete", 3, "click"], [1, "content"], [1, "date"]],
    template: function MbViewerComponent_Template(rf, ctx) {
      if (rf & 1) {
        _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵelementStart"](0, "div", 0)(1, "button", 1);
        _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵlistener"]("click", function MbViewerComponent_Template_button_click_1_listener() {
          return ctx.delete();
        });
        _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵtext"](2, "X");
        _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵelementEnd"]();
        _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵelementStart"](3, "div", 2);
        _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵtext"](4);
        _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵelementEnd"]();
        _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵelementStart"](5, "div", 3);
        _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵtext"](6);
        _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵpipe"](7, "date");
        _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵelementEnd"]()();
      }
      if (rf & 2) {
        _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵadvance"](4);
        _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵtextInterpolate1"](" ", ctx.post.content, " ");
        _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵadvance"](2);
        _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵtextInterpolate1"](" ", _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵpipeBind2"](7, 2, ctx.post.createdAt, "short"), " ");
      }
    },
    dependencies: [_angular_common__WEBPACK_IMPORTED_MODULE_4__.DatePipe],
    styles: [".post[_ngcontent-%COMP%] {\n  background: #49306B;\n  color: #FFF;\n  padding: 2em 2em 1em 1em;\n  margin: 0 0 0.7em 0;\n  position: relative;\n}\n.post[_ngcontent-%COMP%]   .btn-delete[_ngcontent-%COMP%] {\n  position: absolute;\n  right: 1em;\n  top: 1em;\n  width: 1.4em;\n  height: 1.4em;\n  padding: 0;\n  font-size: 0.8em;\n  line-height: 1em;\n  color: #FFFFFF;\n}\n.post[_ngcontent-%COMP%]   .content[_ngcontent-%COMP%] {\n  font-size: 1.2em;\n}\n.post[_ngcontent-%COMP%]   .date[_ngcontent-%COMP%] {\n  text-align: right;\n  font-size: 0.8em;\n}\n/*# sourceURL=webpack://./src/app/mb-viewer/mb-viewer.component.less */\n/*# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbIndlYnBhY2s6Ly8uL3NyYy9hcHAvbWItdmlld2VyL21iLXZpZXdlci5jb21wb25lbnQubGVzcyJdLCJuYW1lcyI6W10sIm1hcHBpbmdzIjoiQUFBQTtFQUNJLG1CQUFBO0VBQ0EsV0FBQTtFQUNBLHdCQUFBO0VBQ0EsbUJBQUE7RUFDQSxrQkFBQTtBQUNKO0FBTkE7RUFRUSxrQkFBQTtFQUNBLFVBQUE7RUFDQSxRQUFBO0VBQ0EsWUFBQTtFQUNBLGFBQUE7RUFDQSxVQUFBO0VBQ0EsZ0JBQUE7RUFDQSxnQkFBQTtFQUNBLGNBQUE7QUFDUjtBQWpCQTtFQW9CUSxnQkFBQTtBQUFSO0FBcEJBO0VBd0JRLGlCQUFBO0VBQ0EsZ0JBQUE7QUFEUiIsInNvdXJjZXNDb250ZW50IjpbIi5wb3N0IHtcbiAgICBiYWNrZ3JvdW5kOiAjNDkzMDZCO1xuICAgIGNvbG9yOiAjRkZGO1xuICAgIHBhZGRpbmc6IDJlbSAyZW0gMWVtIDFlbTtcbiAgICBtYXJnaW46IDAgMCAwLjdlbSAwO1xuICAgIHBvc2l0aW9uOiByZWxhdGl2ZTtcblxuICAgIC5idG4tZGVsZXRlIHtcbiAgICAgICAgcG9zaXRpb246IGFic29sdXRlO1xuICAgICAgICByaWdodDogMWVtO1xuICAgICAgICB0b3A6IDFlbTtcbiAgICAgICAgd2lkdGg6IDEuNGVtO1xuICAgICAgICBoZWlnaHQ6IDEuNGVtO1xuICAgICAgICBwYWRkaW5nOiAwO1xuICAgICAgICBmb250LXNpemU6IDAuOGVtO1xuICAgICAgICBsaW5lLWhlaWdodDogMWVtO1xuICAgICAgICBjb2xvcjogI0ZGRkZGRjtcbiAgICB9XG5cbiAgICAuY29udGVudCB7XG4gICAgICAgIGZvbnQtc2l6ZTogMS4yZW07XG4gICAgfVxuXG4gICAgLmRhdGUge1xuICAgICAgICB0ZXh0LWFsaWduOiByaWdodDtcbiAgICAgICAgZm9udC1zaXplOiAwLjhlbTtcbiAgICB9XG59Il0sInNvdXJjZVJvb3QiOiIifQ== */"]
  });
}

/***/ }),

/***/ 8290:
/*!*********************************!*\
  !*** ./src/app/post.service.ts ***!
  \*********************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

__webpack_require__.r(__webpack_exports__);
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   "PostService": () => (/* binding */ PostService)
/* harmony export */ });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ 2560);
/* harmony import */ var _angular_common_http__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/common/http */ 8987);



class PostService {
  constructor(http) {
    this.http = http;
  }
  create(request) {
    return this.http.post("/api/post", request);
  }
  getPosts(offset = 0) {
    console.log("Loading page", `page: ${Math.ceil(offset / 15)}`, `offset: ${offset}`);
    return this.http.get("/api/post/stream", {
      params: {
        pageSize: 15,
        page: Math.ceil(offset / 15)
      }
    });
  }
  getPost(postId) {
    return this.http.get(`/api/post/${postId}`);
  }
  delete(post) {
    return this.http.delete(`/api/post/${post.id}`);
  }
  static #_ = this.ɵfac = function PostService_Factory(t) {
    return new (t || PostService)(_angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵinject"](_angular_common_http__WEBPACK_IMPORTED_MODULE_1__.HttpClient));
  };
  static #_2 = this.ɵprov = /*@__PURE__*/_angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵdefineInjectable"]({
    token: PostService,
    factory: PostService.ɵfac,
    providedIn: 'root'
  });
}

/***/ }),

/***/ 4431:
/*!*********************!*\
  !*** ./src/main.ts ***!
  \*********************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

__webpack_require__.r(__webpack_exports__);
/* harmony import */ var _angular_platform_browser__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/platform-browser */ 4497);
/* harmony import */ var _app_app_module__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./app/app.module */ 6747);


_angular_platform_browser__WEBPACK_IMPORTED_MODULE_1__.platformBrowser().bootstrapModule(_app_app_module__WEBPACK_IMPORTED_MODULE_0__.AppModule).catch(err => console.error(err));

/***/ })

},
/******/ __webpack_require__ => { // webpackRuntimeModules
/******/ var __webpack_exec__ = (moduleId) => (__webpack_require__(__webpack_require__.s = moduleId))
/******/ __webpack_require__.O(0, ["vendor"], () => (__webpack_exec__(4431)));
/******/ var __webpack_exports__ = __webpack_require__.O();
/******/ }
]);
//# sourceMappingURL=main.js.map