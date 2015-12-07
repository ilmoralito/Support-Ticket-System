var Filter = (function() {
    var filterHeaderState = [
        {
            name: 'Atendidos por',
            open: true
        },
        {
            name: 'Estado',
            open: true
        },
        {
            name: 'Departamentos',
            open: true
        },
        {
            name: 'Etiquetas',
            open: true
        },
        {
            name: 'Tipo',
            open: true
        },
        {
            name: 'Solicitado por',
            open: true
        }
    ];

    var filterHeaderTarget = '.filter-header';

    return {
        init: function() {
            if (!localStorage.filterHeaderState) {
                localStorage.filterHeaderState = JSON.stringify(filterHeaderState);
            }

            var data = JSON.parse(localStorage.filterHeaderState)

            $(filterHeaderTarget).each(function() {
                var _this = $(this);
                var next = _this.next();

                data.forEach(function(item, i) {
                    if (item.name === _this.text()) {
                        if (item.open === true) {
                            next.show();
                        } else {
                            next.hide();
                        }
                    }
                });
            });
        },

        update: function(name) {
            var data = JSON.parse(localStorage.filterHeaderState);

            for (var i = data.length - 1; i >= 0; i--) {
                if (data[i].name === name) {
                    data[i].open = !data[i].open

                    break;
                }
            };

            localStorage.filterHeaderState = JSON.stringify(data);
        }
    }
})();

Filter.init();

$('.filter-header').on('click', function() {
    var self = $(this);

    Filter.update(self.text());

    self.next().toggle();
});
