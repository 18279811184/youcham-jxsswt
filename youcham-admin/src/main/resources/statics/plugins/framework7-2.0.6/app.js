// Dom7
var $$ = Dom7;

// Theme
//var theme = 'auto';
var theme = 'ios';
if (document.location.search.indexOf('theme=') >= 0) {
  theme = document.location.search.split('theme=')[1].split('&')[0];
}

// Init App
var app = new Framework7({
	pushState: true,
  id: 'inspect',
  root: '#app',
  theme: theme,
  modalTitle: "提示",
	modalButtonOk: "确定",
	modalButtonCancel: "取消",
	modalPreloaderTitle: "加载中...",
	modalUsernamePlaceholder: "用户名",
	modalPasswordPlaceholder: "密码",
  data: function () {
    return {
      user: {
        firstName: 'John',
        lastName: 'Doe',
      },
    };
  },
  methods: {
    helloWorld: function () {
      app.dialog.alert('Hello World!');
    },
  },
  routes: routes,
  vi: {
    placementId: 'pltd4o7ibb9rc653x14',
  },

  
});
