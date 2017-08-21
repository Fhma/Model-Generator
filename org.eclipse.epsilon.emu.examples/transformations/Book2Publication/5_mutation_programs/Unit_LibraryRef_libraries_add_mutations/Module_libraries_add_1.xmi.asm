<?xml version = '1.0' encoding = 'ISO-8859-1' ?>
<asm version="1.0" name="0">
	<cp>
		<constant value="Book2Publication"/>
		<constant value="links"/>
		<constant value="NTransientLinkSet;"/>
		<constant value="col"/>
		<constant value="J"/>
		<constant value="main"/>
		<constant value="A"/>
		<constant value="OclParametrizedType"/>
		<constant value="#native"/>
		<constant value="Collection"/>
		<constant value="J.setName(S):V"/>
		<constant value="OclSimpleType"/>
		<constant value="OclAny"/>
		<constant value="J.setElementType(J):V"/>
		<constant value="TransientLinkSet"/>
		<constant value="A.__matcher__():V"/>
		<constant value="A.__exec__():V"/>
		<constant value="self"/>
		<constant value="__resolve__"/>
		<constant value="1"/>
		<constant value="J.oclIsKindOf(J):B"/>
		<constant value="18"/>
		<constant value="NTransientLinkSet;.getLinkBySourceElement(S):QNTransientLink;"/>
		<constant value="J.oclIsUndefined():B"/>
		<constant value="15"/>
		<constant value="NTransientLink;.getTargetFromSource(J):J"/>
		<constant value="17"/>
		<constant value="30"/>
		<constant value="Sequence"/>
		<constant value="2"/>
		<constant value="A.__resolve__(J):J"/>
		<constant value="QJ.including(J):QJ"/>
		<constant value="QJ.flatten():QJ"/>
		<constant value="e"/>
		<constant value="value"/>
		<constant value="resolveTemp"/>
		<constant value="S"/>
		<constant value="NTransientLink;.getNamedTargetFromSource(JS):J"/>
		<constant value="name"/>
		<constant value="__matcher__"/>
		<constant value="A.__matchBook2Publication():V"/>
		<constant value="__exec__"/>
		<constant value="NTransientLinkSet;.getLinksByRule(S):QNTransientLink;"/>
		<constant value="A.__applyBook2Publication(NTransientLink;):V"/>
		<constant value="getAuthors"/>
		<constant value="MBook!Book;"/>
		<constant value=""/>
		<constant value="0"/>
		<constant value="chapters"/>
		<constant value="author"/>
		<constant value="CJ.including(J):CJ"/>
		<constant value="J.asSet():J"/>
		<constant value="J.=(J):J"/>
		<constant value="25"/>
		<constant value=" and "/>
		<constant value="J.+(J):J"/>
		<constant value="26"/>
		<constant value="10:49-10:51"/>
		<constant value="8:2-8:6"/>
		<constant value="8:2-8:15"/>
		<constant value="9:3-9:4"/>
		<constant value="9:3-9:11"/>
		<constant value="8:2-10:3"/>
		<constant value="8:2-10:12"/>
		<constant value="11:3-11:6"/>
		<constant value="11:12-11:15"/>
		<constant value="11:18-11:20"/>
		<constant value="11:12-11:20"/>
		<constant value="14:4-14:11"/>
		<constant value="14:14-14:24"/>
		<constant value="14:4-14:24"/>
		<constant value="12:4-12:14"/>
		<constant value="11:9-15:8"/>
		<constant value="11:3-15:8"/>
		<constant value="8:2-16:3"/>
		<constant value="authorName"/>
		<constant value="acc"/>
		<constant value="getSumPages"/>
		<constant value="nbPages"/>
		<constant value="J.sum():J"/>
		<constant value="19:2-19:6"/>
		<constant value="19:2-19:15"/>
		<constant value="20:3-20:4"/>
		<constant value="20:3-20:12"/>
		<constant value="19:2-21:3"/>
		<constant value="19:2-21:9"/>
		<constant value="f"/>
		<constant value="__matchBook2Publication"/>
		<constant value="Book"/>
		<constant value="IN"/>
		<constant value="MMOF!Classifier;.allInstancesFrom(S):QJ"/>
		<constant value="J.getSumPages():J"/>
		<constant value="J.&gt;(J):J"/>
		<constant value="B.not():B"/>
		<constant value="33"/>
		<constant value="TransientLink"/>
		<constant value="NTransientLink;.setRule(MATL!Rule;):V"/>
		<constant value="b"/>
		<constant value="NTransientLink;.addSourceElement(SJ):V"/>
		<constant value="out"/>
		<constant value="Publication"/>
		<constant value="NTransientLink;.addTargetElement(SJ):V"/>
		<constant value="NTransientLinkSet;.addLink2(NTransientLink;B):V"/>
		<constant value="27:4-27:5"/>
		<constant value="27:4-27:19"/>
		<constant value="27:22-27:23"/>
		<constant value="27:4-27:23"/>
		<constant value="32:3-36:4"/>
		<constant value="__applyBook2Publication"/>
		<constant value="NTransientLink;"/>
		<constant value="NTransientLink;.getSourceElement(S):J"/>
		<constant value="NTransientLink;.getTargetElement(S):J"/>
		<constant value="3"/>
		<constant value="title"/>
		<constant value="J.getAuthors():J"/>
		<constant value="authors"/>
		<constant value="33:13-33:14"/>
		<constant value="33:13-33:20"/>
		<constant value="33:4-33:20"/>
		<constant value="34:15-34:16"/>
		<constant value="34:15-34:29"/>
		<constant value="34:4-34:29"/>
		<constant value="35:15-35:16"/>
		<constant value="35:15-35:30"/>
		<constant value="35:4-35:30"/>
		<constant value="link"/>
	</cp>
	<field name="1" type="2"/>
	<field name="3" type="4"/>
	<operation name="5">
		<context type="6"/>
		<parameters>
		</parameters>
		<code>
			<getasm/>
			<push arg="7"/>
			<push arg="8"/>
			<new/>
			<dup/>
			<push arg="9"/>
			<pcall arg="10"/>
			<dup/>
			<push arg="11"/>
			<push arg="8"/>
			<new/>
			<dup/>
			<push arg="12"/>
			<pcall arg="10"/>
			<pcall arg="13"/>
			<set arg="3"/>
			<getasm/>
			<push arg="14"/>
			<push arg="8"/>
			<new/>
			<set arg="1"/>
			<getasm/>
			<pcall arg="15"/>
			<getasm/>
			<pcall arg="16"/>
		</code>
		<linenumbertable>
		</linenumbertable>
		<localvariabletable>
			<lve slot="0" name="17" begin="0" end="24"/>
		</localvariabletable>
	</operation>
	<operation name="18">
		<context type="6"/>
		<parameters>
			<parameter name="19" type="4"/>
		</parameters>
		<code>
			<load arg="19"/>
			<getasm/>
			<get arg="3"/>
			<call arg="20"/>
			<if arg="21"/>
			<getasm/>
			<get arg="1"/>
			<load arg="19"/>
			<call arg="22"/>
			<dup/>
			<call arg="23"/>
			<if arg="24"/>
			<load arg="19"/>
			<call arg="25"/>
			<goto arg="26"/>
			<pop/>
			<load arg="19"/>
			<goto arg="27"/>
			<push arg="28"/>
			<push arg="8"/>
			<new/>
			<load arg="19"/>
			<iterate/>
			<store arg="29"/>
			<getasm/>
			<load arg="29"/>
			<call arg="30"/>
			<call arg="31"/>
			<enditerate/>
			<call arg="32"/>
		</code>
		<linenumbertable>
		</linenumbertable>
		<localvariabletable>
			<lve slot="2" name="33" begin="23" end="27"/>
			<lve slot="0" name="17" begin="0" end="29"/>
			<lve slot="1" name="34" begin="0" end="29"/>
		</localvariabletable>
	</operation>
	<operation name="35">
		<context type="6"/>
		<parameters>
			<parameter name="19" type="4"/>
			<parameter name="29" type="36"/>
		</parameters>
		<code>
			<getasm/>
			<get arg="1"/>
			<load arg="19"/>
			<call arg="22"/>
			<load arg="19"/>
			<load arg="29"/>
			<call arg="37"/>
		</code>
		<linenumbertable>
		</linenumbertable>
		<localvariabletable>
			<lve slot="0" name="17" begin="0" end="6"/>
			<lve slot="1" name="34" begin="0" end="6"/>
			<lve slot="2" name="38" begin="0" end="6"/>
		</localvariabletable>
	</operation>
	<operation name="39">
		<context type="6"/>
		<parameters>
		</parameters>
		<code>
			<getasm/>
			<pcall arg="40"/>
		</code>
		<linenumbertable>
		</linenumbertable>
		<localvariabletable>
			<lve slot="0" name="17" begin="0" end="1"/>
		</localvariabletable>
	</operation>
	<operation name="41">
		<context type="6"/>
		<parameters>
		</parameters>
		<code>
			<getasm/>
			<get arg="1"/>
			<push arg="0"/>
			<call arg="42"/>
			<iterate/>
			<store arg="19"/>
			<getasm/>
			<load arg="19"/>
			<pcall arg="43"/>
			<enditerate/>
		</code>
		<linenumbertable>
		</linenumbertable>
		<localvariabletable>
			<lve slot="1" name="33" begin="5" end="8"/>
			<lve slot="0" name="17" begin="0" end="9"/>
		</localvariabletable>
	</operation>
	<operation name="44">
		<context type="45"/>
		<parameters>
		</parameters>
		<code>
			<push arg="46"/>
			<store arg="19"/>
			<push arg="28"/>
			<push arg="8"/>
			<new/>
			<load arg="47"/>
			<get arg="48"/>
			<iterate/>
			<store arg="29"/>
			<load arg="29"/>
			<get arg="49"/>
			<call arg="50"/>
			<enditerate/>
			<call arg="51"/>
			<iterate/>
			<store arg="29"/>
			<load arg="19"/>
			<load arg="19"/>
			<push arg="46"/>
			<call arg="52"/>
			<if arg="53"/>
			<push arg="54"/>
			<load arg="29"/>
			<call arg="55"/>
			<goto arg="56"/>
			<load arg="29"/>
			<call arg="55"/>
			<store arg="19"/>
			<enditerate/>
			<load arg="19"/>
		</code>
		<linenumbertable>
			<lne id="57" begin="0" end="0"/>
			<lne id="58" begin="5" end="5"/>
			<lne id="59" begin="5" end="6"/>
			<lne id="60" begin="9" end="9"/>
			<lne id="61" begin="9" end="10"/>
			<lne id="62" begin="2" end="12"/>
			<lne id="63" begin="2" end="13"/>
			<lne id="64" begin="16" end="16"/>
			<lne id="65" begin="17" end="17"/>
			<lne id="66" begin="18" end="18"/>
			<lne id="67" begin="17" end="19"/>
			<lne id="68" begin="21" end="21"/>
			<lne id="69" begin="22" end="22"/>
			<lne id="70" begin="21" end="23"/>
			<lne id="71" begin="25" end="25"/>
			<lne id="72" begin="17" end="25"/>
			<lne id="73" begin="16" end="26"/>
			<lne id="74" begin="0" end="29"/>
		</linenumbertable>
		<localvariabletable>
			<lve slot="2" name="33" begin="8" end="11"/>
			<lve slot="2" name="75" begin="15" end="27"/>
			<lve slot="1" name="76" begin="1" end="29"/>
			<lve slot="0" name="17" begin="0" end="29"/>
		</localvariabletable>
	</operation>
	<operation name="77">
		<context type="45"/>
		<parameters>
		</parameters>
		<code>
			<push arg="28"/>
			<push arg="8"/>
			<new/>
			<load arg="47"/>
			<get arg="48"/>
			<iterate/>
			<store arg="19"/>
			<load arg="19"/>
			<get arg="78"/>
			<call arg="50"/>
			<enditerate/>
			<call arg="79"/>
		</code>
		<linenumbertable>
			<lne id="80" begin="3" end="3"/>
			<lne id="81" begin="3" end="4"/>
			<lne id="82" begin="7" end="7"/>
			<lne id="83" begin="7" end="8"/>
			<lne id="84" begin="0" end="10"/>
			<lne id="85" begin="0" end="11"/>
		</linenumbertable>
		<localvariabletable>
			<lve slot="1" name="86" begin="6" end="9"/>
			<lve slot="0" name="17" begin="0" end="11"/>
		</localvariabletable>
	</operation>
	<operation name="87">
		<context type="6"/>
		<parameters>
		</parameters>
		<code>
			<push arg="88"/>
			<push arg="88"/>
			<findme/>
			<push arg="89"/>
			<call arg="90"/>
			<iterate/>
			<store arg="19"/>
			<load arg="19"/>
			<call arg="91"/>
			<pushi arg="29"/>
			<call arg="92"/>
			<call arg="93"/>
			<if arg="94"/>
			<getasm/>
			<get arg="1"/>
			<push arg="95"/>
			<push arg="8"/>
			<new/>
			<dup/>
			<push arg="0"/>
			<pcall arg="96"/>
			<dup/>
			<push arg="97"/>
			<load arg="19"/>
			<pcall arg="98"/>
			<dup/>
			<push arg="99"/>
			<push arg="100"/>
			<push arg="100"/>
			<new/>
			<pcall arg="101"/>
			<pusht/>
			<pcall arg="102"/>
			<enditerate/>
		</code>
		<linenumbertable>
			<lne id="103" begin="7" end="7"/>
			<lne id="104" begin="7" end="8"/>
			<lne id="105" begin="9" end="9"/>
			<lne id="106" begin="7" end="10"/>
			<lne id="107" begin="25" end="30"/>
		</linenumbertable>
		<localvariabletable>
			<lve slot="1" name="97" begin="6" end="32"/>
			<lve slot="0" name="17" begin="0" end="33"/>
		</localvariabletable>
	</operation>
	<operation name="108">
		<context type="6"/>
		<parameters>
			<parameter name="19" type="109"/>
		</parameters>
		<code>
			<load arg="19"/>
			<push arg="97"/>
			<call arg="110"/>
			<store arg="29"/>
			<load arg="19"/>
			<push arg="99"/>
			<call arg="111"/>
			<store arg="112"/>
			<load arg="112"/>
			<dup/>
			<getasm/>
			<load arg="29"/>
			<get arg="113"/>
			<call arg="30"/>
			<set arg="113"/>
			<dup/>
			<getasm/>
			<load arg="29"/>
			<call arg="114"/>
			<call arg="30"/>
			<set arg="115"/>
			<dup/>
			<getasm/>
			<load arg="29"/>
			<call arg="91"/>
			<call arg="30"/>
			<set arg="78"/>
			<pop/>
		</code>
		<linenumbertable>
			<lne id="116" begin="11" end="11"/>
			<lne id="117" begin="11" end="12"/>
			<lne id="118" begin="9" end="14"/>
			<lne id="119" begin="17" end="17"/>
			<lne id="120" begin="17" end="18"/>
			<lne id="121" begin="15" end="20"/>
			<lne id="122" begin="23" end="23"/>
			<lne id="123" begin="23" end="24"/>
			<lne id="124" begin="21" end="26"/>
			<lne id="107" begin="8" end="27"/>
		</linenumbertable>
		<localvariabletable>
			<lve slot="3" name="99" begin="7" end="27"/>
			<lve slot="2" name="97" begin="3" end="27"/>
			<lve slot="0" name="17" begin="0" end="27"/>
			<lve slot="1" name="125" begin="0" end="27"/>
		</localvariabletable>
	</operation>
</asm>
