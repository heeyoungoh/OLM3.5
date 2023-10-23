


/**
 * 리스트 페이징 클래스
 */
var ListPageForm =
{
	pageNo:1, pageSize:100, totalRecord:0, block:10

	,setPageNo: function(pageNo) {this.pageNo=pageNo;try{$add("pageNo",pageNo);}catch(e){}}
	,getPageNo: function() {return this.pageNo;}
	,setPageSize: function(pageSize) {this.pageSize=pageSize;try{$add("pageSize",pageSize);}catch(e){}}
	,getPageSize: function() {return this.pageSize;}
	,setTotalRecord: function(totalRecord) {this.totalRecord=totalRecord;}
	,getTotalRecord: function() {return this.totalRecord;}
	,setBlock: function(block) {this.block=block;}
	,getBlock: function() {return this.block;}// 페이지 블럭
	,getNo: function(no) {return this.totalRecord+1-Number(no);}

	// [PF-01] 하단 링크 출력(linkID:페이징 데이터가 들어가는 객체ID, subjectID:페이징 기본정보가 들어가는 객체ID)
	,paging: function(linkID,subjectID)
	{
		try{this.pageSize=$("#pageSize").val();if(this.pageSize==null) this.pageSize=100; }catch(e){}
		var totalPage = Math.floor((this.totalRecord-1) / this.pageSize)+1;
		var startPage = Math.floor((this.pageNo-1)/this.block)*this.block+1;
		var endPage = (((startPage-1)+this.block)/this.block)*this.block;

		var nBlock = Math.ceil(this.pageNo/this.block);// 현재 블럭
		var tBlock = Math.ceil(totalPage/this.block);
		var preBlock = (nBlock-1)*this.block;// 이전 블록
		var nextBlock = nBlock*this.block+1;// 다음 블록

		var p = "<div class=\"paginate_complex\">";

		if ( endPage > totalPage ) endPage = totalPage;

		// 최신목록
		if ( this.pageNo > 1 ) p += "<a href='#' onclick=\"ListPageForm.setPageNo(1);fnLinkPage(1);\" class=\"direction prev\"><span></span><span></span> 처음</a> ";
		else p += "<a href='#' class=\"direction prev\"><span></span><span></span> 처음</a> ";

		// 이전페이지 구성
		if ( nBlock> 1 )	p += "<a href='#' onclick=\"ListPageForm.setPageNo("+preBlock+");fnLinkPage("+preBlock+");\" class=\"direction prev\"><span></span> 이전그룹</a> ";
		else p += "<a href='#' class=\"direction prev\"><span></span> 이전그룹</a> ";

		// 페이징
		for(var i=startPage; i<=endPage;i++) {
			if ( this.pageNo == i ) p += "<strong>"+i+"</strong>";// 현재 페이지
			else p += "<a href='#' onclick=\"ListPageForm.setPageNo("+i+");fnLinkPage("+i+");\">"+i+"</a>";// 링크
		}

		// 다음페이지 구성
		if ( nBlock < tBlock ) p += "<a href='#' onclick=\"ListPageForm.setPageNo("+nextBlock+");fnLinkPage("+nextBlock+");\" class=\"direction next\">다음그룹 <span></span></a>";
		else p += "<a href='#' class=\"direction next\">다음그룹 <span></span></a>";

		// 끝목록
		if ( totalPage > this.pageNo ) p += "<a href='#' onclick=\"ListPageForm.setPageNo("+totalPage+");fnLinkPage("+(totalPage)+");\" class=\"direction next\">마지막 <span></span><span></span></a>";
		else p += "<a href='#' class=\"direction next\">마지막 <span></span><span></span></a>";

		p += "</div>";

		$("#"+linkID).html(p);
		this.header(subjectID,totalPage);
	}
	// 타이틀
	,header: function(subjectID,totalPage){$("#"+subjectID).html("<img src='"+fnGetRootUrl()+"cmm/images/memo.gif' align='absmiddle'/> "+ FormatCurrerny(this.totalRecord)+" (현재페이지 : <b style='color:#FF6633'>"+FormatCurrerny(this.pageNo)+"</b>/"+FormatCurrerny(totalPage)+")");	}
};

/* 페이지 링크 function */
function fnLinkPage(pageNo){
	$add('pageNo', pageNo);
	doGetList();
}
