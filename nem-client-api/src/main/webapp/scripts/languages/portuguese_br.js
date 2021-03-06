define({
	id: 'pt',
	name: 'Português',
	texts: {
		preferences: {
			thousandSeparator: '\u2009',
			decimalSeparator: '.'
		},
		faults: { // modelos de mensagens de erro 
            101: 'Arquivo não encontrado.',
            102: 'A carteira não foi criada.',
            103: 'O arquivo da carteira está corrompido. Por favor, restaure o back-up, certifique-se que você tenha o feito.',
            104: 'Senha incorreta. Por favor, insira a senha correta. Não há como recuperá-la!',
            106: 'Antes de começar a usar a carteira, é preciso logar-se. Por favor, forneça a senha e certifique-se de nunca a perder.',
            107: 'A carteira não contém essa conta',
            108: 'A conta não pode ser removida. Provavelmente pelo motivo de que ela tem saldo maior que 0 NEMs ou é a conta primária.',
            109: 'Já existe uma conta com o mesmo nome. Por favor, escolha outro.',
            110: 'A carteira já contem essa conta.',
            202: 'A chave pública não existe',
            305: 'NIS indisponível',
            306: 'Ocorreu um erro que o time de desenvolvedores não havia previsto. Pedimos desculpas por esse problema. Talvez uma nova tentativa possa resolver o problema. Caso contrário, abra uma reclamação de problemas NIS/NCC na comunidade NEM',
            400: 'Está faltando algum parâmetro.',
            404: 'Valor de estratégia de boot inválido.',
            500: 'Falha ao salvar o arquivo de configuração.',
            600: 'NCC requer que o NIS seja inicializado. Por favor, vá em cliente NCC para inicializar o nó local.',
	    601: 'O NIS já foi inicializado.',
            700: 'Falha na colheita. Geralmente esse problema é relacionado com o tanto de NEMs na conta. Para colheitar são necessários, pelo menos, 1000 NEMs.',
            701: 'O prazo final fornecido está no passado. O prazo limite deve estar dentro do período de um dia.',
            702: 'O prazo final está muito no futuro. O prazo limite deve estar dentro do período de um dia.',
            703: 'Saldo insuficiente.',
            704: 'Texto muito longo. Por favor, tente reduzir o tamanho da mensagem.',
            705: 'O Hash de transação já existe no banco de dados, na lista de transações não confirmadas.',
            706: 'A assinatura da transação não pôde ser verificada.',
            707: 'A data da ID de transação está muito no passado.',
            708: 'A data da ID de transação está muito no futuro.',
            709: 'Conta desconhecida, Por favor, faça uma transação para conseguir uma chave pública.',
            901: 'Erro na configuração de modo "off-line".'
        },
        common: {
        	success: 'Successo', //titulo de mensagens de sucesso
        	nisStatus: {
        		nccUnavailable: 'NCC não inicializado',
        		unavailable: 'NIS não inicializado',
        		notBooted: 'NIS necessita ser inicializado. Por favor, abra a carteira e inicialize um nó local via diálogo pop-up.',
        		synchronizing: 'NIS está sincronizando com o bloco {{1}}. Encontrado {{2}} atrás.',
                daysBehind: {
                    0: 'Menos de um dia',
                    1: '1 dia',
                    many: '{{1}} dias'
                },
        		synchronized: 'NIS foi sincronizado!'
        	}
        },
		modals: {
			error: {
				title: 'Oopa!',
				caption: 'ERRO {{1}}'
			},
			confirmDefault: {
				yes: 'Sim',
				no: 'Não'
			},
			settings: {
				title: 'Settings',
				language: {
					label: 'Language'
				},
				remoteServer: {
					tabTitle: 'Remote Server',
					protocol: 'Protocol',
					protocolOptions: {
						http: 'HTTP'
					},
					host: 'Host',
					port: 'Port'
				},
				autoBoot: {
					tabTitle: 'Auto-boot',
					name: 'Node name',
					account: 'Account',
					primaryAccount: 'Primary Account',
					auto: 'Auto boot when a wallet is opened'
				},
				save: 'Save',
				saveSuccess: 'Settings have been saved successfully'
			},
			sendNem: {
				title: 'Enviar NEM',
				labelDesc: 'Esta conta está identificada como {{1}}',
				nullLabelDesc: "Esta conta não tem uma identificação",
				amount: 'Total',
				recipient: "Destinatário",
				message: 'Mensagem',
				encrypt: 'Criptografar mensagem',
				fee: 'Taxa',
				dueBy: 'Devido por', //precisara de correção dependendo do contexto
				resetFee: 'Valor mínimo',
				hours: 'Horas',
				password: 'Senha',
				send: 'Enviar',
				sending: 'Enviando...',
				successMessage: 'Transação enviada com sucesso!'
			},
			clientInfo: {
				title: 'Informações do programa',
				ncc: 'NEM Community Client - NCC',
				signer: 'Signatário',
				remoteServer: 'Servidor Remoto',
				local: 'Local',
				nis: 'NEM Infraestructure Servers - NIS',
				sync: 'Sincronizado',
				notSync: 'Não sincronizado',
				notConnected: 'Não conectado à nuvem de peers',
				loading: 'Carregando...'
			},
			transactionDetails: {
				title: 'Detalhes da transação',
				// This might be block or transaction ID
				id: 'ID',
				// This might be block or transaction Hash
				hash: 'Hash',
				type: 'Tipo de transação',
				pending: 'Pendente',
				outgoing: 'Enviado',
				incoming: 'Recebido',
				self: 'Pessoal',
				sender: 'Remetente',
				recipient: 'Destinatário',
				message: 'Mensagem',
				noMessage: 'Sem mensagem',
				encrypted: 'Mensagem Criptografada',
				time: 'Data e hora',
				confirmations: 'Confirmações',
				amount: 'Quantidade',
				fee: 'Taxa'
			},
			bootLocalNode: {
				title: 'Inicializar nó local',
				account: 'Conta do nó',
				noLabel: '<span class="null">&lt;Sem identificação&gt;</span>',
				wallet: 'Nome da Carteira',
				node: 'Nome para o nó',
				boot: 'Inicializar',
				booting: 'Inicializando...'
			},
			notBootedWarning: {
				title: 'O nó não foi inicializado!',
				message: 'O nó local precisa ser inicializado para fazer transações!'
			},
			closeWallet: {
				title: 'Fechar carteira',
				message: 'Você tem certeza que quer fechar a carteira?'
			},
			createAccount: {
				title: 'Criar nova conta',
				label: 'Identificação',
				wallet: 'Carteira',
				password: "Senha",
				successMessage: 'A conta {{1}} {{#2}}({{2}}){{/2}} foi criada! Por favor, realize um back-up da conta',
				create: 'Criar'
			},
			addAccount: {
				title: 'Adicionar uma Carteira existente',
				privateKey: "Chave privada",
				wallet: 'Carteira',
				password: "Senha",
				successMessage: 'A conta {{1}} {{#2}}({{2}}){{/2}} foi criada!',
				add: 'Adicionar',
				label: 'Identificação'
			},
			setPrimary: {
				title: 'Definir como conta primária',
				account: 'Conta que será definida como primária',
				noLabel: '<span class="null">&lt;Sem identificação&gt;</span>',
				wallet: 'Carteira',
				password: "Senha",
				successMessage: 'Conta {{1}} {{#2}}({{2}}){{/2}} foi definida como primária!',
				set: 'Definir como conta primária',
			},
			changeWalletName: {
				title: 'Trocar o nome da carteira',
				wallet: 'Nome atual',
				newName: 'Novo nome',
				password: "Senha",
				successMessage: 'O nome <em>{{1}}</em> foi alterado para <em>{{2}}</em> com sucesso.',
				change: 'Mudar'
			},
			changeWalletPassword: {
				title: 'Trocar a senha da carteira',
				wallet: 'Carteira',
				password: 'Senha atual',
				newPassword: 'Nova senha',
				confirmPassword: 'Confirmar nova senha',
				successMessage: 'A senha da carteira foi trocada com sucesso',
				change: 'Trocar',
				passwordNotMatchTitle: 'Oopa!',
				passwordNotMatchMessage: 'As senhas não conferem. Por favor, tente novamente.'
			},
			changeAccountLabel: {
				title: 'Trocar identificação da conta',
				label: 'Identificação',
				wallet: 'Carteira',
				password: "Senha",
				successMessage: 'A Identificação {{1}} foi alterada para {{2}}',
				change: 'Troca'
			},
			removeAccount: {
				title: 'Remover conta',
				wallet: 'Senha',
				password: "Senha",
				warning: 'Por favor, tenha certeza que não há saldo em sua conta, ou as moedas serão perdidas para sempre.',
				successMessage: 'A conta {{1}} {{#2}}({{2}}){{/2}} foi removida!',
				remove: 'Remover'
			},
			nisUnavailable: {
				title: 'NIS não está inicializado',
				message: 'NIS não está respondendo, aguardando conexão'
			},
			shutdown: {
				title: 'Fechar programa',
				message: 'Tem certeza que quer fechar o programa (NIS/NCC)?' //talvez precise correção aqui
			}
		},
		landing: {
			logo: 'images/nem_logo.png',
			importSuccess: 'Carteira importada com sucesso!',
			nav: {
				start: 'Início', // ou primeiros passos, dependendo do contexto
				about: 'Sobre NEM',
				help: 'Ajuda'
			},
			main: {
				leftTitle: 'Primeira vez? Crie uma carteira <em>NEM</em>!',
				leftButton: 'Criar nova carteira',
				walletNamePlh: 'Nome para a carteira',
				passwordPlh: 'Senha',
				create: 'Criar',
				rightTitle: 'Já é um <em>NEMbro</em>?', //NEMbro soa estranho? "bro" em pt_BR é sinônimo de Brother
				rightButton: 'Abrir carteira existente',
				openButton: 'Abrir',
				walletsFound: 'Encontrada(s) <strong>{{1}}</strong> <em>carteira(s)</em>', //needs to verify if the "(s)" casues any kind of problem to compilation
				copyright: 'Fotografado por <em>Cas Cornelissen</em>'
			},
			carousel: {
				items: [
					{
						title: 'NCC encripta sua carteira', //Hereinafter I will leave it as is until define what will be written, retur at line 263
						description: '<em>Segurança</em> baseada em forte criptografia para evitar roubo de moedas &amp; ativos.'
					},										
					{
						title: 'NCC encripta sua carteira',
						description: '<em>Segurança</em> baseada em forte criptografia para evitar roubo de moedas &amp; ativos.'
					}
				]
			},
			about: {
				sections: [
					{
						title: 'Como o NCC funciona?',
						paragraphs: [
							'<strong>NCC</strong> providencia acesso aos seus ativos e moedas como uma carteira de moeda digital tradicional faz. Você pode',
							'<strong>NCC</strong> requerer acesso a um servidor <strong>NIS</strong> para operar. O padrão é ter um servidor local ativo (está instalado junto com o <strong>NCC</strong>)',
							'Você também pode configurar acesso a um servidor remoto <strong>NIS</strong>.'
						],
						listItems: [
							'Tenha multiplas carteiras',
							'Defina multiplas carteiras para serem incluidas em uma carteira'
						]
					},
					{
						title: 'O que é &#42;NIS?',
						paragraphs: [
							'Este componente é responsável por manter a nuvem <strong>NEM</strong> viva.',
							'Quanto mais <strong>NIS</strong> melhor será a segurança.',
							'<strong>NIS</strong> é o ponto de acesso a nuvem <strong>NEM</strong> de servidores.'
						],
						legend: '<strong>&#42;NIS</strong> significa <strong>NEM Infraestrutura de Servidores</strong>'
					}
				]
			},
			footer: {
				copyright: '&copy; Copyright 2014. NEM Community Client.'
			}
		},
		wallet: {
			logo: 'images/nem_logo.png',
			lastAccess: 'Aproximadamente {{1}} dia(s) atrás',
			lastAccessJustNow: 'Agora a pouco',
			lastAccessTooltip: 'Ultimo acesso em {{1}}',
			primary: 'primário',
			primaryShort: '1º',
			noLabel: '<Sem identificação>',
			copiedToClipboard: 'Endereço copiado para a área de transfêrencia!',
			actions: {
				refreshInfo: 'Atualizar informações',
				bootLocalNode: 'Inicializar nó local',
				changeWalletName: 'Trocar o nome da carteira',
				changeWalletPassword: 'Trocar a senha da carteira',
				mergeWallets: 'Mesclar carteiras',
				exportWallet: 'Exportar carteira',
				createAccount: 'Criar uma nova Conta',
				addAccount: 'Adicionar uma conta existente',
				changeAccountLabel: 'Trocar a identificação da carteira',
				setPrimary: 'Definir como conta primária',
				removeAccount: 'Remover Conta',
				clientInfo: 'Sobre o NEM/NCC',
				closeWallet: 'Fechar carteira',
				closeProgram: 'Fechar programa',
				copyClipboard: 'Copiar para a área de transferência'
			},
			nav: [
				'Painel Administrativo', //Dashboard
				'Mensagens',
				'Contatos',
				'Transações',
				'Blocos colhidos',
				'Exchange de ativos',
				'Novidades',
				'Aplicações',
				'Contas',
				'Configurações',
				'Fechar programa'
			],
			bootNodeWarning: "Um nó local precisa ser inicializado para poder utilizar o NCC."
		},
		dashboard: {
			assets: {
				title: 'Seus ativos'
			},
			importance: {
				title: 'Pontuação de importância',
				unknown: 'Status desconhecido',
				start: 'Começar a colheita',
				harvesting: 'Colheitando',
				stop: 'Parar colheita',
				description: 'importância para a nuvem de peers'
			},
			transactions: {
				title: 'Transações recentes',
				sendNem: 'Enviar NEM',
				balance: 'Saldo atual',
				syncStatus: '(até o bloco {{1}}{{#2}} : encontrado a {{3}} dias atrás{{/2}})',
				unknown: 'Desconhecido',
				columns: [
					'',
					'Data',
					'Destinatário',
					'Mensagem',
					'',
					'Detalhes',
					'Confirmações',
					'Taxa',
					'Quantidade'
				],
				types: {
					pending: 'Pendente',
					outgoing: 'Enviada',
					incoming: 'Recebida',
					self: 'Enviada para si mesmo',
				},
				noMessage: 'Sem mensagem',
				encrypted: 'Mensagem criptografada',
				view: 'Ver',
				pending: 'Pendente',
				seeAll: 'Ver todas as transações',
				noTransactions: 'Nenhuma transação realizada ainda.'
			},
			nemValue: {
				title: 'Estatísticas desta carteira'
			},
			messages: {
				titleTooltip: 'Mensagens'
			},
			news: {
				titleTooltip: 'Novidades'
			},
			notAvailable: 'Não disponível na versão Alfa'
		},
		transactions: {
			title: 'Transações',
			sendNem: 'Enviar NEM',
			balance: 'Saldo',
			filters: {
				confirmed: 'Confirmadas',
				unconfirmed: 'Não confirmadas',
				incoming: 'Recebidas',
				outgoing: 'Enviadas',
			},
			table: {
				columns: [
					'',
					'Tempo',
					'Remetente/Destinatário',
					'Mensagem',
					'',
					'Detalhes',
					'Confirmações',
					'Taxa',
					'Quantidade'
				],
				types: {
					pending: 'Pendente',
					outgoing: 'Enviada',
					incoming: 'Recebida',
					self: 'Enviada para si mesmo',
				},
				noMessage: 'Sem mensagem',
				encrypted: 'A mensagem está criptografada',
				view: 'Ver', 
				pending: 'Pendente',
				noTransactions: 'Por enquanto nenhuma transação realizada ainda',
				loading: 'Carregando mais transações...'
			}
		},
		harvestedBlocks: {
			title: 'Blocos colhidos',
			feeEarned: 'Taxas ganhas com a colheita dos ultimos 25 blocos',
			table: {
				columns: [ 
					'Altura do bloco',
					'Hora',
					'Hash',
					'Taxa'
				],
				noBlocks: 'Não há blocos colhidos',
				loadMore: 'Ver blocos colhidos anteriormente'
			},
			harvesting: {
				unknown: 'Status desconhecido',
				start: 'Começar a colheita',
				harvesting: 'Colheitando',
				stop: 'Parar de colheitar'
			}
		},
		settings: {
			title: 'Opções',
			settings: [
				{
					name: 'Linguagem'
				}
			],
			save: 'Salvar modificações',
			saveSuccess: 'As modificações foram salvas com sucesso'
		}
	}
});
