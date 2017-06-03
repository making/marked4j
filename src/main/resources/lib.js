(function () {
    var Marked4J = function (marked) {
        this._marked = marked;
        this.renderer = new marked.Renderer();
        this.slugify = function (text) {
            return text.toLowerCase().replace(/[^\w]+/g, '-');
        }
    };
    Marked4J.prototype.marked = function (text) {
        return this._marked(text, {renderer: this.renderer});
    };
    Marked4J.prototype.setOptions = function (options) {
        var opts = JSON.parse(options);
        this._marked.setOptions(opts);
    };
    Marked4J.prototype.enableHeadingIdUriEncoding = function () {
        this.slugify = function (text) {
            return encodeURIComponent(text);
        };
        this.renderer.heading = function (text, level) {
            var escapedText = this.slugify(text);
            return '<h' + level + ' id="' + escapedText + '">' + text + '</h' + level + '>\n';
        }.bind(this);
    };
    Marked4J.prototype.toc = function (text) {
        var tokens = marked.lexer(text);
        tokens = tokens.filter(function (token) {
            if (token.type !== 'heading') {
                return false;
            }
            if (token.depth === 1) {
                // Ignore h1
                return false;
            }
            return true;
        });
        var min = tokens.map(function (token) {
            return token.depth;
        }).reduce(function (x, y) {
            return Math.min(x, y);
        }, 9999);

        return tokens.map(function (token) {
                var id = this.slugify(token.text);
                return repeat('*', 1 + token.depth - min) + ' [' + token.text + '](#' + id + ')';
            }.bind(this)).join('\n') + '\n';
    };
    // Thanks to https://github.com/jonschlinkert/marked-toc
    Marked4J.prototype.insertToc = function (text) {
        var start = '<!-- toc -->';
        var stop = '<!-- tocstop -->';
        var re = /<!-- toc -->([\s\S]+?)<!-- tocstop -->/;

        // remove the existing TOC
        var content = text.replace(re, start);

        // generate new TOC
        var newtoc = '\n\n'
            + start + '\n\n'
            + this.toc(content) + '\n'
            + stop + '\n';

        return text.replace(start, newtoc);
    };

    function repeat(text, n) {
        var ret = '';
        for (var i = 0; i < n; i++) {
            ret = ret + text;
        }
        return ret;
    }

    return new Marked4J(marked);
})();