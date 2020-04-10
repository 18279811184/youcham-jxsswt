//信访1
	xfOption1 = {

		    tooltip: {
		        trigger: 'item',
		        formatter: "{a} <br/>{b}: {c} ({d}%)"
		    },
		    legend: {
		        orient: 'vertical',
		        x: 'right',
		        data:['正常','即将到期','已逾期','严重逾期']
		    },
		    series: [
		        {
		            name:'办理时限情况',
		            type:'pie',
		            radius: ['30%', '60%'],
		            avoidLabelOverlap: true,
		            label: {
		                normal: {
		                    show: true,
		                    position: 'right',formatter: '{b}: {c}'
			                
		                },
		              
		                emphasis: {
		                    show: true,
		                    textStyle: {
		                        fontSize: '30',
		                        fontWeight: 'bold'
		                    }
		                } 
		            },
		            labelLine: {
		                normal: {
		                    show: true
		                }
		            },
		            data:[
		                {value:335, name:'正常'},
		                {value:310, name:'即将到期'},
		                {value:234, name:'已逾期'},
		                {value:135, name:'严重逾期'}
		            ]
		        }
		    ]
	};
	
	
	//信访办理结果
	xfOption2 = {

		    tooltip: {
		        trigger: 'item',
		        formatter: "{a} <br/>{b}: {c} ({d}%)"
		    },
		    legend: {
		        orient: 'vertical',
		        x: 'right',
		        data:['移交','交办','移送']
		    },
		    series: [
		        {
		            name:'处置结果',
		            type:'pie',
		            radius: ['30%', '60%'],
		            avoidLabelOverlap: true,
		            label: {
		                normal: {
		                    show: true,
		                    position: 'right',formatter: '{b}: {c}'
		                },
		                emphasis: {
		                    show: true,
		                    textStyle: {
		                        fontSize: '30',
		                        fontWeight: 'bold'
		                    }
		                }
		            },
		            labelLine: {
		                normal: {
		                    show: true
		                }
		            },
		            data:[
		                {value:535, name:'移交'},
		                {value:210, name:'交办'},
		                {value:234, name:'移送'} 
		            ]
		        }
		    ]
	};
	
	
	//初核办理完成情况
	chOption1 = {

		    tooltip: {
		        trigger: 'item',
		        formatter: "{a} <br/>{b}: {c} ({d}%)"
		    },
		    legend: {
		        orient: 'vertical',
		        x: 'right',
		        data:['正常','即将到期','已逾期','严重逾期']
		    },
		    series: [
		        {
		            name:'办理时限情况',
		            type:'pie',
		            radius: ['30%', '60%'],
		            avoidLabelOverlap: true,
		            label: {
		                normal: {
		                    show: true,
		                    position: 'right',formatter: '{b}: {c}'
		                },
		                emphasis: {
		                    show: true,
		                    textStyle: {
		                        fontSize: '30',
		                        fontWeight: 'bold'
		                    }
		                }
		            },
		            labelLine: {
		                normal: {
		                    show: true
		                }
		            },
		            data:[
		                {value:12, name:'正常'},
		                {value:5, name:'即将到期'},
		                {value:3, name:'已逾期'},
		                {value:1, name:'严重逾期'}
		            ]
		        }
		    ]
	};
	
	
	//初核办理结果
	chOption2 = {

		    tooltip: {
		        trigger: 'item',
		        formatter: "{a} <br/>{b}: {c} ({d}%)"
		    },
		    legend: {
		        orient: 'vertical',
		        x: 'right',
		        data:['情况失实','属实予以立案','部分属实']
		    },
		    series: [
		        {
		            name:'处置结果',
		            type:'pie',
		            radius: ['30%', '60%'],
		            avoidLabelOverlap: true,
		            label: {
		                normal: {
		                    show: true,
		                    position: 'right',formatter: '{b}: {c}'
		                },
		                emphasis: {
		                    show: true,
		                    textStyle: {
		                        fontSize: '30',
		                        fontWeight: 'bold'
		                    }
		                }
		            },
		            labelLine: {
		                normal: {
		                    show: true
		                }
		            },
		            data:[
		                {value:5, name:'情况失实'},
		                {value:11, name:'属实予以立案'},
		                {value:24, name:'部分属实'} 
		            ]
		        }
		    ]
	};
	
	//立案办理完成情况
	laOption1 = {

		    tooltip: {
		        trigger: 'item',
		        formatter: "{a} <br/>{b}: {c} ({d}%)"
		    },
		    legend: {
		        orient: 'vertical',
		        x: 'right',
		        data:['正常','即将到期','已逾期','严重逾期']
		    },
		    series: [
		        {
		            name:'办理时限情况',
		            type:'pie',
		            radius: ['30%', '60%'],
		            avoidLabelOverlap: true,
		            label: {
		                normal: {
		                    show: true,
		                    position: 'right',formatter: '{b}: {c}'
		                },
		                emphasis: {
		                    show: true,
		                    textStyle: {
		                        fontSize: '30',
		                        fontWeight: 'bold'
		                    }
		                }
		            },
		            labelLine: {
		                normal: {
		                    show: true
		                }
		            },
		            data:[
		                {value:6, name:'正常'},
		                {value:2, name:'即将到期'},
		                {value:1, name:'已逾期'},
		                {value:2, name:'严重逾期'}
		            ]
		        }
		    ]
	};


	//立案办理结果
	laOption2 = {

		    tooltip: {
		        trigger: 'item',
		        formatter: "{a} <br/>{b}: {c} ({d}%)"
		    },
		    legend: {
		        orient: 'vertical',
		        x: 'right',
		        data:['予以立案','其它']
		    },
		    series: [
		        {
		            name:'处置结果',
		            type:'pie',
		            radius: ['30%', '60%'],
		            avoidLabelOverlap: true,
		            label: {
		                normal: {
		                    show: true,
		                    position: 'right',formatter: '{b}: {c}'
		                },
		                emphasis: {
		                    show: true,
		                    textStyle: {
		                        fontSize: '30',
		                        fontWeight: 'bold'
		                    }
		                }
		            },
		            labelLine: {
		                normal: {
		                    show: true
		                }
		            },
		            data:[
		                {value:5, name:'予以立案'},
		                {value:0, name:'其它'} 
		            ]
		        }
		    ]
	};
	
	
	//调查办理完成情况
	dcOption1 = {

		    tooltip: {
		        trigger: 'item',
		        formatter: "{a} <br/>{b}: {c} ({d}%)"
		    },
		    legend: {
		        orient: 'vertical',
		        x: 'right',
		        data:['正常','即将到期','已逾期','严重逾期']
		    },
		    series: [
		        {
		            name:'办理时限情况',
		            type:'pie',
		            radius: ['30%', '60%'],
		            avoidLabelOverlap: true,
		            label: {
		                normal: {
		                    show: true,
		                    position: 'right',formatter: '{b}: {c}'
		                },
		                emphasis: {
		                    show: true,
		                    textStyle: {
		                        fontSize: '30',
		                        fontWeight: 'bold'
		                    }
		                }
		            },
		            labelLine: {
		                normal: {
		                    show: true
		                }
		            },
		            data:[
		                {value:12, name:'正常'},
		                {value:5, name:'即将到期'},
		                {value:3, name:'已逾期'},
		                {value:1, name:'严重逾期'}
		            ]
		        }
		    ]
	};


	//案件调查结果
	dcOption2 = {

		    tooltip: {
		        trigger: 'item',
		        formatter: "{a} <br/>{b}: {c} ({d}%)"
		    },
		    legend: {
		        orient: 'vertical',
		        x: 'right',
		        data:['移送审理','其它']
		    },
		    series: [
		        {
		            name:'调查结果',
		            type:'pie',
		            radius: ['30%', '60%'],
		            avoidLabelOverlap: true,
		            label: {
		                normal: {
		                    show: true,
		                    position: 'right',formatter: '{b}: {c}'
		                },
		                emphasis: {
		                    show: true,
		                    textStyle: {
		                        fontSize: '30',
		                        fontWeight: 'bold'
		                    }
		                }
		            },
		            labelLine: {
		                normal: {
		                    show: true
		                }
		            },
		            data:[
		                {value:6, name:'移送审理'},
		                {value:2, name:'其它'} 
		            ]
		        }
		    ]
	};
	
	//审理完成情况
	slOption1 = {

		    tooltip: {
		        trigger: 'item',
		        formatter: "{a} <br/>{b}: {c} ({d}%)"
		    },
		    legend: {
		        orient: 'vertical',
		        x: 'right',
		        data:['正常','即将到期','已逾期','严重逾期']
		    },
		    series: [
		        {
		            name:'办理时限情况',
		            type:'pie',
		            radius: ['30%', '60%'],
		            avoidLabelOverlap: true,
		            label: {
		                normal: {
		                    show: true,
		                    position: 'right',formatter: '{b}: {c}'
		                },
		                emphasis: {
		                    show: true,
		                    textStyle: {
		                        fontSize: '30',
		                        fontWeight: 'bold'
		                    }
		                }
		            },
		            labelLine: {
		                normal: {
		                    show: true
		                }
		            },
		            data:[
		                {value:12, name:'正常'},
		                {value:5, name:'即将到期'},
		                {value:3, name:'已逾期'},
		                {value:1, name:'严重逾期'}
		            ]
		        }
		    ]
	};



	//审理结果
	slOption2 = {

		    tooltip: {
		        trigger: 'item',
		        formatter: "{a} <br/>{b}: {c} ({d}%)"
		    },
		    legend: {
		        orient: 'vertical',
		        x: 'right',
		        data:['审理通过','其它']
		    },
		    series: [
		        {
		            name:'调查结果',
		            type:'pie',
		            radius: ['30%', '60%'],
		            avoidLabelOverlap: true,
		            label: {
		                normal: {
		                    show: true,
		                    position: 'right',formatter: '{b}: {c}'
		                },
		                emphasis: {
		                    show: true,
		                    textStyle: {
		                        fontSize: '30',
		                        fontWeight: 'bold'
		                    }
		                }
		            },
		            labelLine: {
		                normal: {
		                    show: true
		                }
		            },
		            data:[
		                {value:5, name:'审理通过'},
		                {value:1, name:'其它'} 
		            ]
		        }
		    ]
	};
	
	//处分决定完成情况
	cfOption1 = {

		    tooltip: {
		        trigger: 'item',
		        formatter: "{a} <br/>{b}: {c} ({d}%)"
		    },
		    legend: {
		        orient: 'vertical',
		        x: 'right',
		        data:['正常','即将到期','已逾期','严重逾期']
		    },
		    series: [
		        {
		            name:'办理时限情况',
		            type:'pie',
		            radius: ['30%', '60%'],
		            avoidLabelOverlap: true,
		            label: {
		                normal: {
		                    show: true,
		                    position: 'right',formatter: '{b}: {c}'
		                },
		                emphasis: {
		                    show: true,
		                    textStyle: {
		                        fontSize: '30',
		                        fontWeight: 'bold'
		                    }
		                }
		            },
		            labelLine: {
		                normal: {
		                    show: true
		                }
		            },
		            data:[
		                {value:5, name:'正常'},
		                {value:2, name:'即将到期'},
		                {value:0, name:'已逾期'},
		                {value:0, name:'严重逾期'}
		            ]
		        }
		    ]
	};
	

	//处分决定结果
	cfOption2 = {

		    tooltip: {
		        trigger: 'item',
		        formatter: "{a} <br/>{b}: {c} ({d}%)"
		    },
		    legend: {
		        orient: 'vertical',
		        x: 'right',
		        data:['已决定并公示','其它']
		    },
		    series: [
		        {
		            name:'调查结果',
		            type:'pie',
		            radius: ['30%', '60%'],
		            avoidLabelOverlap: true,
		            label: {
		                normal: {
		                    show: true,
		                    position: 'right',formatter: '{b}: {c}'
		                },
		                emphasis: {
		                    show: true,
		                    textStyle: {
		                        fontSize: '30',
		                        fontWeight: 'bold'
		                    }
		                }
		            },
		            labelLine: {
		                normal: {
		                    show: true
		                }
		            },
		            data:[
		                {value:3, name:'已决定并公示'},
		                {value:0, name:'其它'} 
		            ]
		        }
		    ]
	};
	
	var xfChart1 = echarts.init(document.getElementById('xfChart1'),"macarons");
	xfChart1.setOption(xfOption1);
	var xfChart2 = echarts.init(document.getElementById('xfChart2'),"macarons");
	xfChart2.setOption(xfOption2);   	

	var chChart1 = echarts.init(document.getElementById('chChart1'),"macarons");
	chChart1.setOption(chOption1);
	var chChart2 = echarts.init(document.getElementById('chChart2'),"macarons");
	chChart2.setOption(chOption2);   	
	
	var laChart1 = echarts.init(document.getElementById('laChart1'),"macarons");
	laChart1.setOption(laOption1);
	var laChart2 = echarts.init(document.getElementById('laChart2'),"macarons");
	laChart2.setOption(laOption2);   	
	
	var dcChart1 = echarts.init(document.getElementById('dcChart1'),"macarons");
	dcChart1.setOption(dcOption1);
	var dcChart2 = echarts.init(document.getElementById('dcChart2'),"macarons");
	dcChart2.setOption(dcOption2);   	

	var slChart1 = echarts.init(document.getElementById('slChart1'),"macarons");
	slChart1.setOption(slOption1);
	var slChart2 = echarts.init(document.getElementById('slChart2'),"macarons");
	slChart2.setOption(slOption2);   	
	
	var cfChart1 = echarts.init(document.getElementById('cfChart1'),"macarons");
	cfChart1.setOption(cfOption1);
	var cfChart2 = echarts.init(document.getElementById('cfChart2'),"macarons");
	cfChart2.setOption(cfOption2);   
	