declare const Xonomy: any;

export class XonomyModel {

    public static zalbaXonomy = {
        elements: {
            zalba: {
                menu: [
                    {
                        caption: "Add attribute 'id'",
                        action: Xonomy.newAttribute,
                        actionParameter: { name: "id", value: "0" },
                        hideIf(jsElement) {
                            return jsElement.hasAttribute('id');
                        }
                    },
                    {
                        caption: "Add attribute 'status'",
                        action: Xonomy.newAttribute,
                        actionParameter: { name: "status", value: "0" },
                        hideIf(jsElement) {
                            return jsElement.hasAttribute('status');
                        }
                    },
                    {
                        caption: "Add attribute 'broj'",
                        action: Xonomy.newAttribute,
                        actionParameter: { name: "broj", value: "0" },
                        hideIf(jsElement) {
                            return jsElement.hasAttribute('broj');
                        }
                    },
                    {
                        caption: "Add attribute 'username'",
                        action: Xonomy.newAttribute,
                        actionParameter: { name: "username", value: "0" },
                        hideIf(jsElement) {
                            return jsElement.hasAttribute('username');
                        }
                    },
                    {
                        caption: "Add attribute 'poverenikUsername'",
                        action: Xonomy.newAttribute,
                        actionParameter: { name: "poverenikUsername", value: "0" },
                        hideIf(jsElement) {
                            return jsElement.hasAttribute('poverenikUsername');
                        }
                    },
                    {
                        caption: 'Add <uvod>',
                        action: Xonomy.newElementChild,
                        actionParameter: '<uvod></uvod>',
                        hideIf: function (jsElement) {
                            return jsElement.hasChildElement("uvod");
                        },
                    },
                    {
                        caption: 'Add <sadrzaj>',
                        action: Xonomy.newElementChild,
                        actionParameter: '<sadrzaj></sadrzaj>',
                        hideIf: function (jsElement) {
                            return jsElement.hasChildElement("sadrzaj");
                        }
                    }
                ],
                attributes: {
                    "status": {
                        asker: Xonomy.askString,
                        menu: [{
                            caption: 'Delete this @status',
                            action: Xonomy.deleteAttribute
                        }]
                    },
                    "broj": {
                        asker: Xonomy.askString,
                        menu: [{
                            caption: 'Delete this @broj',
                            action: Xonomy.deleteAttribute
                        }]
                    },
                    "username": {
                        asker: Xonomy.askString,
                        menu: [{
                            caption: 'Delete this @username',
                            action: Xonomy.deleteAttribute
                        }]
                    },
                    "poverenikUsername": {
                        asker: Xonomy.askString,
                        menu: [{
                            caption: 'Delete this @poverenikUsername',
                            action: Xonomy.deleteAttribute
                        }]
                    },
                    "id": {
                        asker: Xonomy.askString,
                        menu: [{
                            caption: 'Delete this @id',
                            action: Xonomy.deleteAttribute
                        }]
                    }
                }
            },
            "uvod": {
                mustBeBefore: ['sadrzaj'],
                hasText: false,
                oneliner: false,
                menu: [
                    {
                        caption: 'Delete element',
                        action: Xonomy.deleteElement
                    },
                    {
                        caption: 'Add <broj>',
                        action: Xonomy.newElementChild,
                        actionParameter: '<broj></broj>',
                        hideIf: function (jsElement) {
                            return jsElement.hasChildElement("broj");
                        }
                    },
                    {
                        caption: 'Add <datum>',
                        action: Xonomy.newElementChild,
                        actionParameter: '<datum></datum>',
                        hideIf: function (jsElement) {
                            return jsElement.hasChildElement("datum");
                        }
                    },
                    {
                        caption: 'Add <paragraf>',
                        action: Xonomy.newElementChild,
                        actionParameter: '<paragraf></paragraf>',
                        hideIf: function (jsElement) {
                            return jsElement.hasChildElement("paragraf");
                        }
                    }
                ],
            },
            "broj": {
                mustBeBefore: ['datum', 'paragraf'],
                hasText: true,
                oneliner: true,
            },
            "datum": {
                mustBeBefore: ['paragraf'],
                hasText: true,
                oneliner: true,
            },
            "paragraf": {
                hasText: true,
                inlineMenu: [
                    {
                        caption: "Wrap with <osoba>",
                        action: Xonomy.wrap,
                        actionParameter: {
                            template: "<osoba>$</osoba>", placeholder: "$"
                        }
                    },
                    {
                        caption: "Wrap with <institucija>",
                        action: Xonomy.wrap,
                        actionParameter: {
                            template: "<institucija>$</institucija>", placeholder: "$"
                        }
                    },
                    {
                        caption: "Wrap with <datum>",
                        action: Xonomy.wrap,
                        actionParameter: {
                            template: "<datum>$</datum>", placeholder: "$"
                        }
                    },
                    {
                        caption: "Wrap with <mesto>",
                        action: Xonomy.wrap,
                        actionParameter: {
                            template: "<mesto>$</mesto>", placeholder: "$"
                        }
                    },
                    {
                        caption: "Wrap with <zakon>",
                        action: Xonomy.wrap,
                        actionParameter: {
                            template: "<zakon>$</zakon>", placeholder: "$"
                        }
                    },
                    {
                        caption: "Wrap with <taksa>",
                        action: Xonomy.wrap,
                        actionParameter: {
                            template: "<taksa>$</taksa>", placeholder: "$"
                        }
                    },
                ]
            },
            "taksa": {
                hasText: true,
                oneliner: true,
            },
            "zakon": {
                hasText: true,
                menu: [
                    {
                        caption: "Unwrap <zakon>",
                        action: Xonomy.unwrap
                    }
                ],
                inlineMenu: [
                    {
                        caption: "Wrap with <clan>",
                        action: Xonomy.wrap,
                        actionParameter: {
                            template: "<clan>$</clan>", placeholder: "$"
                        }
                    },
                    {
                        caption: "Wrap with <tacka>",
                        action: Xonomy.wrap,
                        actionParameter: {
                            template: "<tacka>$</tacka>", placeholder: "$"
                        }
                    },
                    {
                        caption: "Wrap with <naziv>",
                        action: Xonomy.wrap,
                        actionParameter: {
                            template: "<naziv>$</naziv>", placeholder: "$"
                        }
                    },
                    {
                        caption: "Wrap with <glasnik>",
                        action: Xonomy.wrap,
                        actionParameter: {
                            template: "<glasnik>$</glasnik>", placeholder: "$"
                        }
                    },
                ]
            },
            "clan": {
                hasText: true,
                oneliner: true,
            },
            "tacka": {
                hasText: true,
                oneliner: true,
            },
            "glasnik": {
                hasText: true,
                oneliner: true,
            },
            "institucija": {
                hasText: true,
                oneliner: true,
                menu: [
                    {
                        caption: "Unwrap <institucija>",
                        action: Xonomy.unwrap
                    }
                ],
                inlineMenu: [
                    {
                        caption: "Wrap with <naziv>",
                        action: Xonomy.wrap,
                        actionParameter: {
                            template: "<naziv>$</naziv>", placeholder: "$"
                        }
                    },
                    {
                        caption: "Wrap with <mesto>",
                        action: Xonomy.wrap,
                        actionParameter: {
                            template: "<mesto>$</mesto>", placeholder: "$"
                        }
                    }
                ]
            },
            "naziv": {
                mustBeBefore: ['mesto'],
                hasText: true,
                oneliner: true,
            },
            "mesto": {
                hasText: true,
                oneliner: true,
            },
            "osoba": {
                hasText: true,
                menu: [
                    {
                        caption: "Unwrap <osoba>",
                        action: Xonomy.unwrap
                    }
                ],
                inlineMenu: [
                    {
                        caption: "Wrap with <ime>",
                        action: Xonomy.wrap,
                        actionParameter: {
                            template: "<ime>$</ime>", placeholder: "$"
                        }
                    },
                    {
                        caption: "Wrap with <prezime>",
                        action: Xonomy.wrap,
                        actionParameter: {
                            template: "<prezime>$</prezime>", placeholder: "$"
                        }
                    }
                ]
            },
            "sadrzaj": {
                mustBeBefore: [],
                hasText: false,
                oneliner: false,
                menu: [
                    {
                        caption: 'Delete element',
                        action: Xonomy.deleteElement
                    },
                    {
                        caption: 'Add <resenje>',
                        action: Xonomy.newElementChild,
                        actionParameter: '<resenje></resenje>',
                        hideIf: function (jsElement) {
                            return jsElement.hasChildElement("resenje");
                        }
                    },
                    {
                        caption: 'Add <obrazlozenje>',
                        action: Xonomy.newElementChild,
                        actionParameter: '<obrazlozenje></obrazlozenje>',
                        hideIf: function (jsElement) {
                            return jsElement.hasChildElement("obrazlozenje");
                        }
                    },
                    {
                        caption: 'Add <poverenik>',
                        action: Xonomy.newElementChild,
                        actionParameter: '<poverenik></poverenik>',
                        hideIf: function (jsElement) {
                            return jsElement.hasChildElement("poverenik");
                        }
                    },
                ],
            },
            "resenje": {
                mustBeBefore: ['obrazlozenje', 'poverenik'],
                hasText: false,
                oneliner: false,
                menu: [
                    {
                        caption: 'Add <paragraf>',
                        action: Xonomy.newElementChild,
                        actionParameter: '<paragraf></paragraf>',
                        hideIf: function (jsElement) {
                            return jsElement.hasChildElement("paragraf");
                        }
                    },
                    {
                        caption: 'Add <naslov>',
                        action: Xonomy.newElementChild,
                        actionParameter: '<naslov></naslov>',
                        hideIf: function (jsElement) {
                            return jsElement.hasChildElement("naslov");
                        }
                    },
                ]
            },
            "naslov": {
                mustBeBefore: ['paragraf'],
                hasText: true,
                oneliner: true,
                menu: [
                    {
                        caption: "Add attribute 'res:nivo'",
                        action: Xonomy.newAttribute,
                        actionParameter: { name: "res:nivo", value: "0" },
                        hideIf(jsElement) {
                            return jsElement.hasAttribute('res:nivo');
                        }
                    },
                ],
                attributes: {
                    "res:nivo": {
                        asker: Xonomy.askString,
                        menu: [
                            {
                                caption: 'Delete this @res:nivo',
                                action: Xonomy.deleteAttribute
                            }
                        ]
                    },
                }
            },
            "res:nivo": {
                hasText: true,
                oneliner: true,
            },
            "obrazlozenje": {
                mustBeBefore: ['poverenik'],
                hasText: true,
                oneliner: true,
                menu: [
                    {
                        caption: 'Add <naslov>',
                        action: Xonomy.newElementChild,
                        actionParameter: '<naslov></naslov>',
                    },
                    {
                        caption: 'Add <paragraf>',
                        action: Xonomy.newElementChild,
                        actionParameter: '<paragraf></paragraf>',
                    },
                ]
            },
            "poverenik": {
                mustBeBefore: [],
                hasText: true,
                oneliner: true,
                menu: [
                    {
                        caption: 'Add <ime>',
                        action: Xonomy.newElementChild,
                        actionParameter: '<ime></ime>',
                    },
                    {
                        caption: 'Add <prezime>',
                        action: Xonomy.newElementChild,
                        actionParameter: '<prezime></prezime>',
                    },
                ]
            },
            "ime": {
                hasText: true,
                oneliner: true,
                menu: [
                    {
                        caption: "Unwrap <ime>",
                        action: Xonomy.unwrap
                    }
                ],
            },
            "prezime": {
                hasText: true,
                oneliner: true,
                menu: [
                    {
                        caption: "Unwrap <prezime>",
                        action: Xonomy.unwrap
                    }
                ],
            }
        }
    }
}