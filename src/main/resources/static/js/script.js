

			$('#chart-container').orgchart({
				'data' : $('#ul-data'),
				 'verticalDepth': 5,
				 'createNode': function($node, data) {
					    var secondMenuIcon = $('<i>', {
					      'class': 'fa fa-info-circle second-menu-icon',
					      click: function() {
					        $(this).siblings('.second-menu').toggle();
					      }
					    });
					    var secondMenu = '<a class="second-menu" href="'+data.value+'" >apa</a>';
					    $node.append(secondMenuIcon).append(secondMenu);
					  }
			});
